package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.HouseInfo;
import cc.mrbird.febs.cos.entity.RentEvaluation;
import cc.mrbird.febs.cos.dao.RentEvaluationMapper;
import cc.mrbird.febs.cos.service.IHouseInfoService;
import cc.mrbird.febs.cos.service.IRentEvaluationService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentEvaluationServiceImpl extends ServiceImpl<RentEvaluationMapper, RentEvaluation> implements IRentEvaluationService {

    private final IHouseInfoService houseInfoService;

    /**
     * 分页获取租房评价信息
     *
     * @param page 分页对象
     * @param rentEvaluation 租房评价信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRentEvaluationPage(Page<RentEvaluation> page, RentEvaluation rentEvaluation) {
        return baseMapper.selectRentEvaluationPage(page, rentEvaluation);
    }

    /**
     * 获取房屋评价信息
     *
     * @param houseCode 房屋编号
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, BigDecimal> selectEvaluationByHouse(String houseCode) {
        LinkedHashMap<String, BigDecimal> result = new LinkedHashMap<String, BigDecimal>() {
            {
                put("environment", BigDecimal.ZERO);
                put("facility", BigDecimal.ZERO);
                put("device", BigDecimal.ZERO);
                put("traffic", BigDecimal.ZERO);
                put("noise", BigDecimal.ZERO);
                put("price", BigDecimal.ZERO);
                put("overall", BigDecimal.ZERO);
            }
        };
        // 评价信息
        List<RentEvaluation> rentEvaluationList = this.list(Wrappers.<RentEvaluation>lambdaQuery().eq(RentEvaluation::getHouseCode, houseCode));
        if (CollectionUtil.isEmpty(rentEvaluationList)) {
            result.replaceAll((k, v) -> BigDecimal.valueOf(80));
            return result;
        }
        // 使用并行流遍历 rentEvaluationList 集合
        rentEvaluationList.parallelStream().forEach(evaluation -> {
            result.put("environment", result.get("environment").add(evaluation.getEnvironmentScore()));
            result.put("facility", result.get("facility").add(evaluation.getFacilityScore()));
            result.put("device", result.get("device").add(evaluation.getDeviceScore()));
            result.put("traffic", result.get("traffic").add(evaluation.getTrafficScore()));
            result.put("noise", result.get("noise").add(evaluation.getNoiseScore()));
            result.put("price", result.get("price").add(evaluation.getPriceScore()));
            result.put("overall", result.get("overall").add(evaluation.getOverallScore()));
        });

        int size = rentEvaluationList.size();
        result.replaceAll((k, v) -> v.divide(BigDecimal.valueOf(size), 2, RoundingMode.HALF_UP));
        return result;
    }

    /**
     * 获取小区评价信息
     *
     * @param communityCode 小区编号
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, BigDecimal> selectEvaluationByCommunity(String communityCode) {
        LinkedHashMap<String, BigDecimal> result = new LinkedHashMap<String, BigDecimal>() {
            {
                put("environment", BigDecimal.ZERO);
                put("facility", BigDecimal.ZERO);
                put("device", BigDecimal.ZERO);
                put("traffic", BigDecimal.ZERO);
                put("noise", BigDecimal.ZERO);
                put("price", BigDecimal.ZERO);
                put("overall", BigDecimal.ZERO);
            }
        };
        List<HouseInfo> houseList = houseInfoService.list(Wrappers.<HouseInfo>lambdaQuery().eq(HouseInfo::getCommunityCode, communityCode));
        List<String> houseCodeList = houseList.stream().map(HouseInfo::getCode).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(houseCodeList)) {
            result.replaceAll((k, v) -> BigDecimal.valueOf(80));
            return result;
        }

        // 评价信息
        List<RentEvaluation> rentEvaluationList = this.list(Wrappers.<RentEvaluation>lambdaQuery().in(RentEvaluation::getHouseCode, houseCodeList));
        if (CollectionUtil.isEmpty(rentEvaluationList)) {
            result.replaceAll((k, v) -> BigDecimal.valueOf(80));
            return result;
        }
        // 使用并行流遍历 rentEvaluationList 集合
        rentEvaluationList.parallelStream().forEach(evaluation -> {
            result.put("environment", result.get("environment").add(evaluation.getEnvironmentScore()));
            result.put("facility", result.get("facility").add(evaluation.getFacilityScore()));
            result.put("device", result.get("device").add(evaluation.getDeviceScore()));
            result.put("traffic", result.get("traffic").add(evaluation.getTrafficScore()));
            result.put("noise", result.get("noise").add(evaluation.getNoiseScore()));
            result.put("price", result.get("price").add(evaluation.getPriceScore()));
            result.put("overall", result.get("overall").add(evaluation.getOverallScore()));
        });

        int size = rentEvaluationList.size();
        result.replaceAll((k, v) -> v.divide(BigDecimal.valueOf(size), 2, RoundingMode.HALF_UP));
        return result;
    }
}
