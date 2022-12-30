package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.dao.StaffInfoMapper;
import cc.mrbird.febs.cos.service.IStaffInfoService;
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
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfo> implements IStaffInfoService {

    private final RepairInfoMapper repairInfoMapper;

    /**
     * 分页获取员工信息
     *
     * @param page 分页对象
     * @param staffInfo 员工信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectStaffPage(Page<StaffInfo> page, StaffInfo staffInfo) {
        return baseMapper.selectStaffPage(page, staffInfo);
    }

    /**
     * 查询员工工作状态
     *
     * @param day 时间
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectStaffWorkStatus(String day) {
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        // 所有员工信息
        List<StaffInfo> staffInfoList = this.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStatus, 1));
        if (CollectionUtil.isEmpty(staffInfoList)) {
            return Collections.emptyList();
        }
        // 根据时间获取维修单信息
        List<RepairInfo> repairList = repairInfoMapper.selectRepairByDate();
        Map<Integer, List<RepairInfo>> repairStaffMap = repairList.stream().collect(Collectors.groupingBy(RepairInfo::getStaffId));
        staffInfoList.forEach(e -> {
            boolean ex = repairStaffMap != null && repairStaffMap.get(e.getId()) != null;
            LinkedHashMap<String, Object> item = new LinkedHashMap<String, Object>() {
                {
                    put(e.getName(), ex);
                }
            };
            result.add(item);
        });
        return result;
    }
}
