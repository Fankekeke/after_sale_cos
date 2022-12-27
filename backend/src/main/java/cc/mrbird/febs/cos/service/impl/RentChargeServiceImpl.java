package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.controller.po.RentChargePo;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.RentChargeMapper;
import cc.mrbird.febs.cos.entity.vo.RentChargeDetailVo;
import cc.mrbird.febs.cos.entity.vo.RentChargeVo;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
public class RentChargeServiceImpl extends ServiceImpl<RentChargeMapper, RentCharge> implements IRentChargeService {

    private final ICommunityInfoService communityInfoService;

    private final IHouseInfoService houseInfoService;

    private final IStaffInfoService staffInfoService;

    private final ISysCityService cityService;

    /**
     * 查询租房信息包括房屋编号与小区编号
     *
     * @return 结果
     */
    @Override
    public List<RentCharge> selectRentChargeWithHouse() {
        return baseMapper.selectRentChargeWithHouse();
    }

    /**
     * 获取房屋出租负责信息
     *
     * @param rentCharge 参数
     * @return 结果
     */
    @Override
    public List<RentChargeVo> selectRentChargeList(RentChargePo rentCharge) {
        return baseMapper.selectRentChargeList(rentCharge);
    }

    /**
     * 获取租房信息详情
     *
     * @param chargeId id
     * @return 结果
     */
    @Override
    public RentChargeDetailVo selectChargeDetail(Integer chargeId) {
        return baseMapper.selectChargeDetail(chargeId);
    }

    /**
     * 获取房屋租赁信息地图
     *
     * @param rentCharge 参数
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRentChargeByMap(RentChargePo rentCharge) {
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(rentCharge);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        return null;
    }

    /**
     * 根据时间获取出租信息
     *
     * @param year  统计年度
     * @param month 统计月度
     * @return 结果
     */
    @Override
    public List<RentChargeVo> selectRentChargeByDate(String year, String month) {
        RentChargePo param = new RentChargePo();
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return rentChargeVoList;
        }
        boolean monthCheck = StrUtil.isNotEmpty(month);
        rentChargeVoList = rentChargeVoList.stream().filter(e -> year.equals(e.getCheckYear())).collect(Collectors.toList());
        if (monthCheck && StrUtil.isNotEmpty(month)) {
            rentChargeVoList = rentChargeVoList.stream().filter(e -> month.equals(e.getCheckMonth())).collect(Collectors.toList());
        }
        return rentChargeVoList;
    }

    /**
     * 获取各小区租房情况
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRentChargeByCommunity(String communityCode) {
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        RentChargePo param = new RentChargePo();
        if (StrUtil.isNotEmpty(communityCode)) {
            param.setCommunityCode(communityCode);
        }
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        List<CommunityInfo> communityInfoList = communityInfoService.list(Wrappers.<CommunityInfo>lambdaQuery().eq(CommunityInfo::getDelFlag, 0).eq(StrUtil.isNotEmpty(communityCode), CommunityInfo::getCode, communityCode));
        Map<String, List<RentChargeVo>> rentChargeMap = rentChargeVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getCommunityCode())).collect(Collectors.groupingBy(RentChargeVo::getCommunityCode));
        if (CollectionUtil.isEmpty(communityInfoList) || CollectionUtil.isEmpty(rentChargeMap)) {
            return result;
        }
        for (CommunityInfo communityInfo : communityInfoList) {
            List<RentChargeVo> rentChargeVoItem = rentChargeMap.get(communityInfo.getCode());
            LinkedHashMap<String, Object> rentChargeItem = new LinkedHashMap<String, Object>() {
                {
                    put("community", communityInfo);
                    put("rent", Collections.emptyList());
                }
            };
            if (CollectionUtil.isNotEmpty(rentChargeVoItem)) {
                rentChargeItem.put("rent", rentChargeVoItem);
                continue;
            }
            result.add(rentChargeItem);
        }
        return result;
    }

    /**
     * 房屋出租信息
     *
     * @param houseCode 房屋编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRentChargeByHouse(String houseCode) {
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        RentChargePo param = new RentChargePo();
        if (StrUtil.isNotEmpty(houseCode)) {
            param.setHouseCode(houseCode);
        }
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        List<HouseInfo> houseList = houseInfoService.list(Wrappers.<HouseInfo>lambdaQuery().eq(StrUtil.isNotEmpty(houseCode), HouseInfo::getCode, houseCode).eq(HouseInfo::getDelFlag, 0));
        Map<String, List<RentChargeVo>> rentChargeVoMap = rentChargeVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getHouseCode())).collect(Collectors.groupingBy(RentChargeVo::getHouseCode));
        if (CollectionUtil.isEmpty(houseList) || CollectionUtil.isEmpty(rentChargeVoMap)) {
            return result;
        }
        for (HouseInfo houseInfo : houseList) {
            List<RentChargeVo> rentChargeVoItem = rentChargeVoMap.get(houseInfo.getCode());
            LinkedHashMap<String, Object> rentChargeItem = new LinkedHashMap<String, Object>() {
                {
                    put("house", houseInfo);
                    put("rent", Collections.emptyList());
                }
            };
            if (CollectionUtil.isNotEmpty(rentChargeVoItem)) {
                rentChargeItem.put("rent", rentChargeVoItem);
            }
            result.add(rentChargeItem);
        }
        return result;
    }

    /**
     * 获取员工负责情况
     *
     * @param staffCode 员工编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRentChargeByStaff(String staffCode) {
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        RentChargePo param = new RentChargePo();
        if (StrUtil.isNotEmpty(staffCode)) {
            param.setStaffCode(staffCode);
        }
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        List<StaffInfo> staffInfoList = staffInfoService.list(Wrappers.<StaffInfo>lambdaQuery().eq(StrUtil.isNotEmpty(staffCode), StaffInfo::getStaffCode, staffCode).eq(StaffInfo::getStaffStatus, 1));
        Map<String, List<RentChargeVo>> rentChargeMap = rentChargeVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getStaffCode())).collect(Collectors.groupingBy(RentChargeVo::getStaffCode));
        if (CollectionUtil.isEmpty(rentChargeMap) || CollectionUtil.isEmpty(staffInfoList)) {
            return result;
        }
        for (StaffInfo staff : staffInfoList) {
            List<RentChargeVo> rentChargeVoItem = rentChargeMap.get(staff.getStaffCode());
            LinkedHashMap<String, Object> rentChargeItem = new LinkedHashMap<String, Object>() {
                {
                    put("staff", staff);
                    put("rent", Collections.emptyList());
                }
            };
            if (CollectionUtil.isNotEmpty(rentChargeVoItem)) {
                rentChargeItem.put("rent", rentChargeVoItem);
            }
            result.add(rentChargeItem);
        }
        return result;
    }

    /**
     * 根据省份查询租房信息
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectHouseInfoByProvince() {
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        // 所有省份信息
        List<SysCity> sysCityList = cityService.list(Wrappers.<SysCity>lambdaQuery().eq(SysCity::getParentId, 0));
        // 获取所有租房信息
        RentChargePo param = new RentChargePo();
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        Map<String, List<RentChargeVo>> rentChargeMap = rentChargeVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getProvince())).collect(Collectors.groupingBy(RentChargeVo::getProvince));
        for (SysCity city : sysCityList) {
            List<RentChargeVo> rentChargeVoItem = rentChargeMap.get(city.getName());
            LinkedHashMap<String, Object> rentChargeItem = new LinkedHashMap<String, Object>() {
                {
                    put("name", city.getName());
                }
            };
            if (CollectionUtil.isEmpty(rentChargeVoItem)) {
                result.add(rentChargeItem);
                continue;
            }
            rentChargeItem.put("rent", rentChargeVoItem);
            result.add(rentChargeItem);
        }
        return result;
    }

    /**
     * 获取员工销售情况
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRentTopByStaff() {
        // 返回结果
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        List<StaffInfo> staffInfoList = staffInfoService.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStaffStatus, 0));
        if (CollectionUtil.isEmpty(staffInfoList)) {
            return result;
        }
        RentChargePo param = new RentChargePo();
        param.setPlanStatus(2);
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        Map<String, List<RentChargeVo>> rentChargeMap = rentChargeVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getStaffCode())).collect(Collectors.groupingBy(RentChargeVo::getStaffCode));
        for (StaffInfo staff : staffInfoList) {
            List<RentChargeVo> rentChargeVoItem = rentChargeMap.get(staff.getStaffCode());
            LinkedHashMap<String, Object> rentChargeItem = new LinkedHashMap<String, Object>() {
                {
                    put("staff", staff);
                    put("rent", Collections.emptyList());
                }
            };
            if (CollectionUtil.isNotEmpty(rentChargeVoItem)) {
                rentChargeItem.put("rent", rentChargeVoItem);
            }
            result.add(rentChargeItem);
        }
        return result;
    }

    /**
     * 根据小区编号获取当前房源
     *
     * @param communityCode 小区编号
     * @return 结果
     */
    @Override
    public Integer selectRentCountByCommunity(String communityCode) {
        RentChargePo param = new RentChargePo();
        if (StrUtil.isNotEmpty(communityCode)) {
            param.setCommunityCode(communityCode);
        }
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        return rentChargeVoList.size();
    }
}
