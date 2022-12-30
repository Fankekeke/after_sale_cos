package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.PaymentRecordMapper;
import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (roleId == 74) {
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
        }
        // 客户信息展示
        if (roleId == 75) {

        }
        // 公告信息
        List<BulletinInfo> bulletinList = bulletinInfoService.list();
        result.put("bulletin", bulletinList);
        return result;
    }
}
