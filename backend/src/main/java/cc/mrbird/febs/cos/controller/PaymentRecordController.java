package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.service.IPaymentRecordService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/payment-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentRecordController {

    private final IPaymentRecordService paymentRecordService;

    /**
     * 分页获取缴费记录
     *
     * @param page          分页对象
     * @param paymentRecord 参数
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PaymentRecord> page, PaymentRecord paymentRecord) {
        return R.ok(paymentRecordService.selectRecordPage(page, paymentRecord));
    }

    /**
     * 添加缴费信息
     *
     * @param paymentRecord 缴费信息
     * @return 结果
     */
    @PostMapping
    public R save(PaymentRecord paymentRecord) {
        return R.ok(paymentRecordService.savePaymentRecord(paymentRecord));
    }

}
