package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.OrderInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    /**
     * 分页获取工单信息
     *
     * @param page      分页对象
     * @param orderInfo 工单信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, OrderInfo orderInfo);

    /**
     * 查询主页信息
     *
     * @param roleId 角色ID
     * @param userId 用户ID
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData(Long roleId, String userId);

    /**
     * 工单绑定维修人员
     *
     * @param orderCode 工单编号
     * @param staffId   员工ID
     * @return 结果
     */
    boolean setOrderRepairUser(String orderCode, Integer staffId);

    /**
     * 自动派单
     *
     * @param orderCode 工单编号
     */
    void automaticDispatch(String orderCode);
}
