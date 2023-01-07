package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RepairInfo;
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
     * @param page       分页对象
     * @param repairInfo 维修信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRepairPage(Page<RepairInfo> page, RepairInfo repairInfo);

    /**
     * 创建维修单
     *
     * @param staffId 员工ID
     * @return 结果
     */
    boolean saveRepair(Integer staffId);

    /**
     * 维修单详情
     *
     * @param repairCode 维修编号
     * @return 结果
     */
    LinkedHashMap<String, Object> selectRepairDetail(String repairCode);

    /**
     * 根据员工获取维修信息
     *
     * @param userId userId
     * @return 结果
     */
    LinkedHashMap<String, Object> selectRepairByStaff(String userId);
}
