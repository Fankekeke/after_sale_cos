package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.dao.CommunityInfoMapper;
import cc.mrbird.febs.cos.dao.SysCityMapper;
import cc.mrbird.febs.cos.entity.CommunityInfo;
import cc.mrbird.febs.cos.entity.HousePriceTrend;
import cc.mrbird.febs.cos.dao.HousePriceTrendMapper;
import cc.mrbird.febs.cos.entity.SysCity;
import cc.mrbird.febs.cos.entity.vo.PriceTrendRankVo;
import cc.mrbird.febs.cos.service.IHousePriceTrendService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HousePriceTrendServiceImpl extends ServiceImpl<HousePriceTrendMapper, HousePriceTrend> implements IHousePriceTrendService {

    private final CommunityInfoMapper communityInfoMapper;

    private final SysCityMapper cityMapper;

    /**
     * 分页获取房价走势信息
     *
     * @param page            分页对象
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectPriceTrendPage(Page<HousePriceTrend> page, HousePriceTrend housePriceTrend) {
        return baseMapper.selectPriceTrendPage(page, housePriceTrend);
    }

    /**
     * 获取本年度房价排名
     *
     * @return 结果
     */
    @Override
    public List<PriceTrendRankVo> selectTrendByCommunity(String year, String province) {
        if (StrUtil.isEmpty(year)) {
            // 获取当前年度
            year = StrUtil.toString(DateUtil.year(new Date()));
        }
        List<HousePriceTrend> priceTrendList = baseMapper.selectPriceTrendByCommunityCode(null);
        String finalYear = year;
        priceTrendList = priceTrendList.stream().filter(e -> finalYear.equals(e.getYear())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(priceTrendList)) {
            return Collections.emptyList();
        }
        Map<String, List<HousePriceTrend>> priceTrendMap = priceTrendList.stream().collect(Collectors.groupingBy(HousePriceTrend::getCommunityCode));
        // 获取所有小区信息编号
        List<CommunityInfo> communityInfoList = communityInfoMapper.selectList(Wrappers.<CommunityInfo>lambdaQuery().eq(CommunityInfo::getDelFlag, 0).eq(StrUtil.isNotEmpty(province), CommunityInfo::getProvince, province));
        if (CollectionUtil.isEmpty(communityInfoList)) {
            return Collections.emptyList();
        }
        Map<String, String> communityMap = communityInfoList.stream().collect(Collectors.toMap(CommunityInfo::getCode, CommunityInfo::getCommunityName));
        // 返回结果
        List<PriceTrendRankVo> result = new ArrayList<>();
        for (String communityCode : communityMap.keySet()) {
            String communityName = communityMap.get(communityCode);
            if (StrUtil.isEmpty(communityName)) {
                continue;
            }
            PriceTrendRankVo priceTrendRank = new PriceTrendRankVo();
            priceTrendRank.setName(communityName);
            List<HousePriceTrend> trendList = priceTrendMap.get(communityCode);
            if (CollectionUtil.isEmpty(trendList)) {
                result.add(priceTrendRank);
                continue;
            }
            BigDecimal trend = trendList.stream().map(HousePriceTrend::getHousePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            priceTrendRank.setTrend(trend.divide(BigDecimal.valueOf(trendList.size()), 2, RoundingMode.HALF_UP));
            result.add(priceTrendRank);
        }
        // 排序
        return result.stream().sorted(Comparator.comparing(PriceTrendRankVo::getTrend)).collect(Collectors.toList());
    }

    /**
     * 根据省份获取走势数据
     *
     * @param year 统计年度
     * @return 结果
     */
    @Override
    public List<PriceTrendRankVo> selectTrendByProvince(String year) {
        if (StrUtil.isEmpty(year)) {
            // 获取当前年份
            year = StrUtil.toString(DateUtil.year(new Date()));
        }
        List<HousePriceTrend> priceTrendList = baseMapper.selectPriceTrendByCommunityCode(null);
        String finalYear = year;
        priceTrendList = priceTrendList.stream().filter(e -> finalYear.equals(e.getYear())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(priceTrendList)) {
            return Collections.emptyList();
        }
        List<String> communityCodeList = priceTrendList.stream().map(HousePriceTrend::getCommunityCode).collect(Collectors.toList());
        // 获取小区信息
        List<CommunityInfo> communityInfoList = communityInfoMapper.selectList(Wrappers.<CommunityInfo>lambdaQuery().eq(CommunityInfo::getDelFlag, 0).in(CommunityInfo::getCode, communityCodeList));
        if (CollectionUtil.isEmpty(communityInfoList)) {
            return Collections.emptyList();
        }
        // 按照省份转MAP
        Map<String, List<CommunityInfo>> communityByProvinceMap = communityInfoList.stream().filter(e -> StrUtil.isNotEmpty(e.getProvince())).collect(Collectors.groupingBy(CommunityInfo::getProvince));
        // 获取省份信息
        List<SysCity> cityList = cityMapper.selectList(Wrappers.<SysCity>lambdaQuery().eq(SysCity::getParentId, 0));
        if (CollectionUtil.isEmpty(cityList)) {
            return Collections.emptyList();
        }
        List<PriceTrendRankVo> result = new ArrayList<>();
        for (SysCity city : cityList) {
            PriceTrendRankVo item = new PriceTrendRankVo();
            item.setName(city.getName());
            List<CommunityInfo> communityInfos = communityByProvinceMap.get(city.getName());
            if (CollectionUtil.isEmpty(communityInfos)) {
                result.add(item);
                continue;
            }
            List<String> communityCodeItemList = communityInfos.stream().map(CommunityInfo::getCode).collect(Collectors.toList());
            List<HousePriceTrend> priceTrendItem = priceTrendList.stream().filter(e -> communityCodeItemList.contains(e.getCommunityCode())).collect(Collectors.toList());
            BigDecimal priceTrend = priceTrendItem.stream().map(HousePriceTrend::getHousePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            item.setTrend(priceTrend.divide(BigDecimal.valueOf(priceTrendItem.size()), 2, RoundingMode.HALF_UP));
            result.add(item);
        }
        return result;
    }

    /**
     * 获取房价走势信息
     *
     * @param communityCode 小区编号
     * @param year          年
     * @param month         月
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, BigDecimal> selectHousePriceTrend(String communityCode, String year, String month) {
        String type = StrUtil.isEmpty(month) ? "1" : "2";
        List<HousePriceTrend> houseTrendList = baseMapper.selectPriceTrendByCommunityCode(communityCode);
        if (CollectionUtil.isEmpty(houseTrendList)) {
            return null;
        }
        List<String> dateList = new ArrayList<>();
        switch (type) {
            case "1":
                int currentYear = DateUtil.year(new Date());
                dateList = new ArrayList<>(Arrays.asList(StrUtil.toString(year), StrUtil.toString(currentYear - 1), StrUtil.toString(currentYear - 2), StrUtil.toString(currentYear - 3), StrUtil.toString(currentYear - 4), StrUtil.toString(currentYear - 5)));
                break;
            case "2":
                dateList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
                break;
            default:
        }
        LinkedHashMap<String, BigDecimal> housePriceMap = new LinkedHashMap<>(16);
        if ("1".equals(type)) {
            Map<String, List<HousePriceTrend>> housePriceTrendMap = houseTrendList.stream().collect(Collectors.groupingBy(HousePriceTrend::getYear));
            for (String date : dateList) {
                List<HousePriceTrend> housePriceTrendList = housePriceTrendMap.get(date);
                if (CollectionUtil.isEmpty(housePriceTrendList)) {
                    housePriceMap.put(date, BigDecimal.ZERO);
                    continue;
                }
                BigDecimal price = housePriceTrendList.stream().map(HousePriceTrend::getHousePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                housePriceMap.put(date, price.divide(BigDecimal.valueOf(housePriceTrendList.size()), 0, RoundingMode.HALF_UP));
            }
        } else {
            houseTrendList = houseTrendList.stream().filter(e -> year.equals(e.getYear())).collect(Collectors.toList());
            // 按照月度分组
            Map<String, List<HousePriceTrend>> housePriceTrendMap = houseTrendList.stream().collect(Collectors.groupingBy(HousePriceTrend::getMonth));
            for (String date : dateList) {
                List<HousePriceTrend> housePriceTrendList = housePriceTrendMap.get(date);
                if (CollectionUtil.isEmpty(housePriceTrendList)) {
                    housePriceMap.put(date, BigDecimal.ZERO);
                    continue;
                }
                BigDecimal price = housePriceTrendList.stream().map(HousePriceTrend::getHousePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                housePriceMap.put(date, price.divide(BigDecimal.valueOf(housePriceTrendList.size()), 0, RoundingMode.HALF_UP));
            }
        }
        return housePriceMap;
    }
}
