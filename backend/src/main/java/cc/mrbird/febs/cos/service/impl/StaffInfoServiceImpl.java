package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.dao.RentChargeMapper;
import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.dao.StaffInfoMapper;
import cc.mrbird.febs.cos.service.IRentChargeService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfo> implements IStaffInfoService {

    private final RentChargeMapper rentChargeMapper;

    /**
     * 获取员工分页信息
     *
     * @param page      分页对象
     * @param staffInfo 员工信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectStaffPage(Page<StaffInfo> page, StaffInfo staffInfo) {
        return baseMapper.selectStaffPage(page, staffInfo);
    }

    /**
     * 获取员工业务统计
     *
     * @param staffType 员工类型
     * @return 结果
     */
    @Override
    public List<StaffInfo> selectStaffKpi(Integer staffType) throws Exception {
        List<StaffInfo> staffInfoList = this.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStaffType, staffType).eq(StaffInfo::getStaffStatus, 1));
        if (CollectionUtil.isEmpty(staffInfoList)) {
            return staffInfoList;
        }

        // 查找租房记录
        List<RentCharge> rentChargeList = rentChargeMapper.selectList(Wrappers.<RentCharge>lambdaQuery().ne(RentCharge::getPlanStatus, 3));
        if (CollectionUtil.isEmpty(rentChargeList)) {
            return staffInfoList;
        }
        // 根据员工编号转MAP
        Map<String, List<RentCharge>> rentChargeMap = rentChargeList.stream().collect(Collectors.groupingBy(RentCharge::getStaffCode));
        for (StaffInfo staff : staffInfoList) {
            List<RentCharge> rentCharges = rentChargeMap.get(staff.getStaffCode());
            if (CollectionUtil.isEmpty(rentCharges)) {
                staff.setSellNum(0);
                staff.setWaitSellNum(0);
                continue;
            }
            int sellNum = (int) rentCharges.stream().filter(e -> e.getPlanStatus() == 2).count();
            staff.setSellNum(sellNum);
            staff.setWaitSellNum(rentCharges.size() - sellNum);
        }
        return staffInfoList;
    }
}
