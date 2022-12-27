package cc.mrbird.febs.salm.service;

import cc.mrbird.febs.salm.entity.RepairInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IRepairInfoService extends IService<RepairInfo> {

    /**
     * 分页获取维修信息
     *
     * @param page 分页对象
     * @param repairInfo 维修信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRepairPage(Page<RepairInfo> page, RepairInfo repairInfo);
}
