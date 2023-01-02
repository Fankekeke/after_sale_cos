package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements IRepairInfoService {

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
}
