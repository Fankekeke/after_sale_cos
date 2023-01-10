package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoController {

    private final IOrderInfoService orderInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取工单信息
     *
     * @param page      分页对象
     * @param orderInfo 工单信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<OrderInfo> page, OrderInfo orderInfo) {
        return R.ok(orderInfoService.selectOrderPage(page, orderInfo));
    }

    /**
     * 工单驳回
     *
     * @param orderCode 工单编号
     * @return 结果
     */
    @GetMapping("/reject/{orderCode}")
    public R rejectOrder(@PathVariable("orderCode") String orderCode) {
        return R.ok(orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getStatus, 7).eq(OrderInfo::getOrderCode, orderCode)));
    }

    /**
     * 修改工单状态
     *
     * @param orderCode 工单编号
     * @param status    status
     * @return 结果
     */
    @GetMapping("/set/status")
    public R setOrderStatus(@RequestParam("orderCode") String orderCode, @RequestParam("status") Integer status) {
        return R.ok(orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getStatus, status).eq(OrderInfo::getOrderCode, orderCode)));
    }

    /**
     * 工单分配
     *
     * @param staffId 员工ID
     * @param date    预约时间
     * @param amount  收费价格
     * @param remark  备注
     * @return 结果
     */
    @GetMapping("/distribute")
    public R orderDistribute(@RequestParam("orderName") String orderName, @RequestParam("orderCode") String orderCode, @RequestParam("staffId") Integer staffId, @RequestParam("date") String date, @RequestParam("money") BigDecimal amount, @RequestParam("remark") String remark) {
        return R.ok(orderInfoService.orderDistribute(orderName, orderCode, staffId, date, amount, remark));
    }

    /**
     * 根据维修编号获取工单信息
     *
     * @param repairCode 维修编号
     * @return 结果
     */
    @GetMapping("/detail/repair/{repairCode}")
    public R selectOrderDetail(@PathVariable("repairCode") String repairCode) {
        return R.ok(orderInfoService.selectOrderDetail(repairCode));
    }

    /**
     * 查询主页信息
     *
     * @param roleId 角色ID
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/home/data")
    public R homeData(@RequestParam("roleId") Long roleId, @RequestParam("userId") String userId) {
        return R.ok(orderInfoService.homeData(roleId, userId));
    }

    /**
     * 工单绑定维修人员
     *
     * @param orderCode 工单编号
     * @param staffId   员工ID
     * @return 结果
     */
    @GetMapping("/bind/repair")
    public R setOrderRepairUser(@RequestParam("orderCode") String orderCode, @RequestParam("staffId") Integer staffId) {
        return R.ok(orderInfoService.setOrderRepairUser(orderCode, staffId));
    }

    /**
     * 新增工单信息
     *
     * @param orderInfo 工单信息
     * @return 结果
     */
    @PostMapping
    public R save(OrderInfo orderInfo) {
        // 查询用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, orderInfo.getUserId()));
        orderInfo.setCustomerId(userInfo.getId());
        orderInfo.setOrderCode("ORD-" + System.currentTimeMillis());
        orderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        orderInfo.setStatus(0);
        return R.ok(orderInfoService.save(orderInfo));
    }

    /**
     * 修改工单信息
     *
     * @param orderInfo 工单信息
     * @return 结果
     */
    @PutMapping
    public R edit(OrderInfo orderInfo) {
        return R.ok(orderInfoService.updateById(orderInfo));
    }

    /**
     * 删除工单信息
     *
     * @param ids ids
     * @return 工单信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(orderInfoService.removeByIds(ids));
    }
}
