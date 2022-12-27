package cc.mrbird.febs.salm.service.impl;

import cc.mrbird.febs.salm.entity.RepairInfo;
import cc.mrbird.febs.salm.dao.RepairInfoMapper;
import cc.mrbird.febs.salm.service.IRepairInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
