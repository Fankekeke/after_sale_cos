package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.PaymentRecordMapper;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final IBulletinInfoService bulletinInfoService;

    private final IUserInfoService userInfoService;

    private final IStaffInfoService staffInfoService;

    private final IRepairInfoService repairInfoService;

    private final IPaymentRecordService paymentRecordService;

    private final PaymentRecordMapper paymentRecordMapper;

    private final IReserveInfoService reserveInfoService;

    /**
     * 分页获取工单信息
     *
     * @param page 分页对象
     * @param orderInfo 工单信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, OrderInfo orderInfo) {
        return baseMapper.selectOrderPage(page, orderInfo);
    }

    /**
     * 查询主页信息
     *
     * @param roleId 角色ID
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData(Long roleId, String userId) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 管理员展示信息
        if (roleId == 73) {
            // 本月收益
            BigDecimal incomeMonth = baseMapper.selectIncomeMonth();
            // 本月工单
            Integer workOrderMonth = baseMapper.selectWorkOrderMonth();
            // 本月已完成
            Integer completedWorkOrder = baseMapper.selectCompletedWorkOrder();
            // 维修工单
            Integer completedRepairOrder = baseMapper.selectCompletedRepairOrder();
            // 客户数量
            Integer userNum = userInfoService.count();
            // 员工数量
            Integer staffNum = staffInfoService.count();
            // 工单数量
            Integer orderNum = this.count();
            // 缴费收益
            BigDecimal amount = paymentRecordMapper.selectAmountPrice();
            // 员工工作状态
            List<LinkedHashMap<String, Object>> staffWorkStatus = staffInfoService.selectStaffWorkStatus(DateUtil.formatDate(new Date()));
            // 10天内缴费记录
            List<LinkedHashMap<String, Object>> paymentRecord = paymentRecordMapper.selectPaymentRecord();
            // 十天内工单数量
            List<LinkedHashMap<String, Object>> orderRecord = baseMapper.selectOrderRecord();
            // 工单服务类型统计
            List<LinkedHashMap<String, Object>> orderRate = baseMapper.selectOrderServiceTypeRate();
            result.put("incomeMonth", incomeMonth);
            result.put("workOrderMonth", workOrderMonth);
            result.put("completedWorkOrder", completedWorkOrder);
            result.put("completedRepairOrder", completedRepairOrder);
            result.put("userNum", userNum);
            result.put("staffNum", staffNum);
            result.put("orderNum", orderNum);
            result.put("amount", amount);
            result.put("staffWorkStatus", staffWorkStatus);
            result.put("paymentRecord", paymentRecord);
            result.put("orderRecord", orderRecord);
            result.put("orderRate", orderRate);
        }
        // 客户信息展示
        if (roleId == 75) {
            // 进行中工单

        }
        // 公告信息
        List<BulletinInfo> bulletinList = bulletinInfoService.list();
        result.put("bulletin", bulletinList);
        return result;
    }

    /**
     * 工单绑定维修人员
     *
     * @param orderCode 工单编号
     * @param staffId   员工ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setOrderRepairUser(String orderCode, Integer staffId) {
        // 获取工单信息
        OrderInfo orderInfo = this.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderCode, orderCode));
        orderInfo.setStaffId(staffId);
        // 获取维修单，如果没有则新建
        if (StrUtil.isEmpty(orderInfo.getRepairCode())) {
            RepairInfo repairInfo = new RepairInfo();
            repairInfo.setRepairCode("RE-" + System.currentTimeMillis());
            repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            repairInfo.setRepairStatus(1);
            repairInfo.setStaffId(staffId);
            repairInfoService.save(repairInfo);

            // 修改工单维修编号和维修人员
            orderInfo.setRepairCode(repairInfo.getRepairCode());
        } else {
            // 查询维修单信息
            RepairInfo repairInfo = repairInfoService.getOne(Wrappers.<RepairInfo>lambdaQuery().eq(RepairInfo::getRepairCode, orderInfo.getRepairCode()));
            // 修改维修人员信息
            repairInfo.setStaffId(staffId);
            repairInfoService.updateById(repairInfo);
        }
        return this.updateById(orderInfo);
    }

    /**
     * 自动派单
     *
     * @param orderCode 工单编号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void automaticDispatch(String orderCode) {
        // 校验工单编号
        if (StrUtil.isEmpty(orderCode)) {
            return;
        }
        // 获取工单信息
        OrderInfo orderInfo = this.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderCode, orderCode));
        // 判断服务类型
        if (orderInfo.getServerType() != 1) {
            return;
        }
        // 查询当前当前工作状态空闲员工
        List<LinkedHashMap<String, Object>> staffWorkStatus = staffInfoService.selectStaffWorkStatus(DateUtil.formatDate(new Date()));
        if (CollectionUtil.isEmpty(staffWorkStatus)) {
            return;
        }
        for (LinkedHashMap<String, Object> workStatus : staffWorkStatus) {
            if (!(Boolean) workStatus.get("status")) {
                RepairInfo repairInfo = new RepairInfo();
                repairInfo.setRepairCode("RE-" + System.currentTimeMillis());
                repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
                repairInfo.setRepairStatus(1);
                repairInfo.setStaffId(Integer.getInteger(workStatus.get("id").toString()));
                repairInfoService.save(repairInfo);

                // 修改工单维修编号和维修人员
                orderInfo.setRepairCode(repairInfo.getRepairCode());
                return;
            }
        }
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean orderDistribute(String orderName, String orderCode, Integer staffId, String date, BigDecimal amount, String remark) {
        // 获取工单信息
        OrderInfo orderInfo = this.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderCode, orderCode));
        // 添加预约记录
        ReserveInfo reserveInfo = new ReserveInfo();
        reserveInfo.setUserId(orderInfo.getCustomerId());
        reserveInfo.setRemark(orderInfo.getRemark());
        reserveInfo.setImages(orderInfo.getImages());
        reserveInfo.setProductId(orderInfo.getProductId());
        reserveInfo.setReserveDate(date);
        reserveInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        reserveInfo.setOpenFlag(0);
        reserveInfo.setOrderCode(orderCode);
        reserveInfoService.save(reserveInfo);
        // 添加维修记录
        RepairInfo repairInfo = new RepairInfo();
        repairInfo.setRepairStatus(0);
        repairInfo.setRepairCode("RE-" + System.currentTimeMillis());
        repairInfo.setStaffId(staffId);
        repairInfo.setRemark(remark);
        repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        repairInfoService.save(repairInfo);
        // 更新工单状态
        orderInfo.setStaffId(staffId);
        orderInfo.setOrderName(orderName);
        orderInfo.setMoney(amount);
        orderInfo.setRepairCode(repairInfo.getRepairCode());
        orderInfo.setStatus(1);
        return this.updateById(orderInfo);
    }

    /**
     * 根据维修编号获取工单信息
     *
     * @param repairCode 维修编号
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectOrderDetail(String repairCode) {
        return baseMapper.selectOrderDetail(repairCode);
    }
}
