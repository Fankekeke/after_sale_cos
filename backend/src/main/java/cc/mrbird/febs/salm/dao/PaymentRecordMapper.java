package cc.mrbird.febs.salm.dao;

import cc.mrbird.febs.salm.entity.PaymentRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

    /**
     * 分页获取缴费记录信息
     *
     * @param page 分页对象
     * @param paymentRecord 缴费记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPaymentRecordPage(Page<PaymentRecord> page, @Param("paymentRecord") PaymentRecord paymentRecord);
}
