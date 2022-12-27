package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PaymentRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IPaymentRecordService extends IService<PaymentRecord> {

    /**
     * 分页获取缴费记录
     *
     * @param page          分页对象
     * @param paymentRecord 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<PaymentRecord> page, PaymentRecord paymentRecord);

    /**
     * 添加缴费信息
     *
     * @param paymentRecord 缴费信息
     * @return 结果
     */
    boolean savePaymentRecord(PaymentRecord paymentRecord);

    /**
     * 缴费前查询合同信息
     * @param contractCode 合同编号
     * @return 结果
     */
    LinkedHashMap<String, Object> selectContract(String contractCode);
}
