package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.RentChargeMapper;
import cc.mrbird.febs.cos.entity.CommunityInfo;
import cc.mrbird.febs.cos.dao.CommunityInfoMapper;
import cc.mrbird.febs.cos.entity.HouseInfo;
import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.service.ICommunityInfoService;
import cc.mrbird.febs.cos.service.IHouseInfoService;
import cc.mrbird.febs.cos.service.IRentChargeService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommunityInfoServiceImpl extends ServiceImpl<CommunityInfoMapper, CommunityInfo> implements ICommunityInfoService {

    private final IHouseInfoService houseInfoService;

    private final RentChargeMapper rentChargeMapper;

    /**
     * 分页获取小区信息
     *
     * @param page 分页对象
     * @param communityInfo 小区信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectCommunityPage(Page<CommunityInfo> page, CommunityInfo communityInfo) {
        return baseMapper.selectCommunityPage(page, communityInfo);
    }

    /**
     * 远程获取小区信息
     *
     * @param name 小区信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> remoteCommunity(String name) {
        return baseMapper.remoteCommunity(name);
    }

    /**
     * 根据小区编号获取房屋信息
     *
     * @param code 小区编号
     * @return 结果
     */
    @Override
    public List<HouseInfo> selectHouseByCode(String code) {
        return houseInfoService.list(Wrappers.<HouseInfo>lambdaQuery().eq(HouseInfo::getCommunityCode, code));
    }

    /**
     * 根据小区编号获取租房信息
     *
     * @param code 小区编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectHouseRentByCode(String code) {
        return baseMapper.selectHouseRentByCode(code);
    }

    /**
     * 获取小区房屋业务统计
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectHouseSell() {
        List<CommunityInfo> communityList = this.list();
        if (CollectionUtil.isEmpty(communityList)) {
            return Collections.emptyList();
        }
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        communityList.forEach(item -> {
            result.add(new LinkedHashMap<String, Object>(){
                {
                    put("communityCode", item.getCode());
                    put("communityName", item.getCommunityName());
                    put("waitSellNum", 0);
                    put("sellNum", 0);
                }
            });
        });

        // 查找租房记录
        List<RentCharge> rentChargeList = rentChargeMapper.selectRentChargeWithHouse();
        if (CollectionUtil.isEmpty(rentChargeList)) {
            return result;
        }
        // 根据小区编号转Map
        Map<String, List<RentCharge>> rentChargeMap = rentChargeList.stream().collect(Collectors.groupingBy(RentCharge::getCommunityCode));
        for (LinkedHashMap<String, Object> community : result) {
            List<RentCharge> rentCharges = rentChargeMap.get(community.get("communityCode").toString());
            if (CollectionUtil.isEmpty(rentCharges)) {
                continue;
            }
            int sellNum = (int) rentCharges.stream().filter(e -> e.getPlanStatus() == 2).count();
            int waitSellNum = (int) rentCharges.stream().filter(e -> e.getPlanStatus() == 1).count();
            community.put("waitSellNum", waitSellNum);
            community.put("sellNum", sellNum);
        }
        return result;
    }
}
