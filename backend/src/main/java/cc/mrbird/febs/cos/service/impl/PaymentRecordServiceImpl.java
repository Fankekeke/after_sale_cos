package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.DeliveryReviewMapper;
import cc.mrbird.febs.cos.entity.DeliveryReview;
import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.dao.PaymentRecordMapper;
import cc.mrbird.febs.cos.service.IPaymentRecordService;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements IPaymentRecordService {

    private final DeliveryReviewMapper deliveryReviewMapper;

    /**
     * 分页获取缴费记录
     *
     * @param page          分页对象
     * @param paymentRecord 参数
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<PaymentRecord> page, PaymentRecord paymentRecord) {
        return baseMapper.selectRecordPage(page, paymentRecord);
    }

    /**
     * 添加缴费信息
     *
     * @param paymentRecord 缴费信息
     * @return 结果
     */
    @Override
    public boolean savePaymentRecord(PaymentRecord paymentRecord) {
        // 缴费时间
        paymentRecord.setCreateDate(DateUtil.formatDate(new Date()));
        return this.save(paymentRecord);
    }

    /**
     * 缴费前查询合同信息
     * @param contractCode 合同编号
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectContract(String contractCode) {
        if (StrUtil.isEmpty(contractCode)) {
            return null;
        }
        // 合同信息
        DeliveryReview deliveryReview = deliveryReviewMapper.selectOne(Wrappers.<DeliveryReview>lambdaQuery().eq(DeliveryReview::getContractCode, contractCode));
        if (deliveryReview == null) {
            return null;
        }
        // 根据合同编号获取缴费信息
        List<PaymentRecord> recordList = this.list(Wrappers.<PaymentRecord>lambdaQuery().eq(PaymentRecord::getContractCode, contractCode).eq(PaymentRecord::getPaymentType, "1"));
        recordList = recordList.stream().sorted(Comparator.comparing(PaymentRecord::getCreateDate)).collect(Collectors.toList());
        // 上次缴费记录
        PaymentRecord lastRecord = recordList.get(recordList.size() - 1);
        // 缴费记录
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setPaymentType("1");
        paymentRecord.setContractCode(lastRecord.getContractCode());
        paymentRecord.setRentUserCode(lastRecord.getRentUserCode());
        paymentRecord.setRentDay(lastRecord.getRentDay());
        paymentRecord.setCreateDate(DateUtil.formatDate(new Date()));
        // 计算本次缴费开始时间及结束时间
        String currentStartDate = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parse(lastRecord.getEndDate()), 1));
        String currentEndDate = DateUtil.formatDate(DateUtil.offsetMonth(DateUtil.parse(currentStartDate), 3));
        boolean inData = DateUtil.isIn(new Date(), DateUtil.parseDate(currentEndDate), DateUtil.parseDate(deliveryReview.getEndLive()));

        // 返回结果
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("startDate", currentStartDate);
                put("endDate", currentEndDate);
                put("isOver", false);
                put("duration", 0);
                put("amount", BigDecimal.ZERO);
            }
        };
        if (inData) {
            int duration = "1".equals(deliveryReview.getPayType()) ? 1 : 3;
            result.put("duration", duration);
            result.put("amount", deliveryReview.getContractPrice().multiply(BigDecimal.valueOf(duration)));
        } else {
            result.put("isOver", true);
            // 计算时间差
            long betweenDay = DateUtil.between(DateUtil.parseDate(lastRecord.getEndDate()), DateUtil.parseDate(deliveryReview.getEndLive()), DateUnit.DAY);
            result.put("duration", betweenDay);
            // 计算日租金
            BigDecimal rentDay = deliveryReview.getContractPrice().divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP);
            result.put("amount", rentDay.multiply(BigDecimal.valueOf(betweenDay)));
        }
        return result;
    }
}
