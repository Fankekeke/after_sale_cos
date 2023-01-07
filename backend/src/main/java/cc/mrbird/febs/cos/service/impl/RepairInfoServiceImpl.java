package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.StaffInfoMapper;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements IRepairInfoService {

    private final StaffInfoMapper staffInfoMapper;

    /**
     * 分页获取维修信息
     *
     * @param page 分页对象
     * @param repairInfo 维修信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRepairPage(Page<RepairInfo> page, RepairInfo repairInfo) {
        return baseMapper.selectRepairPage(page, repairInfo);
    }

    /**
     * 创建维修单
     *
     * @param staffId 员工ID
     * @return 结果
     */
    @Override
    public boolean saveRepair(Integer staffId) {
        RepairInfo repairInfo = new RepairInfo();
        repairInfo.setRepairCode("RE-" + System.currentTimeMillis());
        repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        repairInfo.setRepairStatus(0);
        repairInfo.setStaffId(staffId);
        return this.save(repairInfo);
    }

    /**
     * 维修单详情
     *
     * @param repairCode 维修编号
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectRepairDetail(String repairCode) {
        return baseMapper.selectRepairDetail(repairCode);
    }

    /**
     * 根据员工获取维修信息
     *
     * @param userId userId
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectRepairByStaff(String userId) {
        // 获取员工信息
        StaffInfo staffInfo = staffInfoMapper.selectOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, userId));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        List<RepairInfo> repairInfoList = baseMapper.selectRepairByStaff(staffInfo.getId());
        // 维修任务列表
        result.put("repairList", repairInfoList);
        // 待接受任务
        result.put("accept", repairInfoList.stream().filter(e -> e.getRepairStatus() == 0).collect(Collectors.toList()));
        return result;
    }
}
