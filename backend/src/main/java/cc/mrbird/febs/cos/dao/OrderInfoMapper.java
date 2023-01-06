package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 分页获取工单信息
     *
     * @param page      分页对象
     * @param orderInfo 工单信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, @Param("orderInfo") OrderInfo orderInfo);

    /**
     * 十天内工单记录
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderRecord();

    /**
     * 查询工单服务类型比例
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderServiceTypeRate();

    /**
     * 根据维修编号获取工单信息
     *
     * @param repairCode 维修编号
     * @return 结果
     */
    LinkedHashMap<String, Object> selectOrderDetail(@Param("repairCode") String repairCode);

    /**
     * 本月收益
     *
     * @return 结果
     */
    BigDecimal selectIncomeMonth();

    /**
     * 本月工单
     *
     * @return 结果
     */
    Integer selectWorkOrderMonth();

    /**
     * 本月已完成工单
     *
     * @return 结果
     */
    Integer selectCompletedWorkOrder();

    /**
     * 本月维修工单
     *
     * @return 结果
     */
    Integer selectCompletedRepairOrder();
}
