package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PaymentRecord;
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
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

    /**
     * 分页获取缴费记录
     *
     * @param page          分页对象
     * @param paymentRecord 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<PaymentRecord> page, @Param("paymentRecord") PaymentRecord paymentRecord);

    /**
     * 本月收入
     *
     * @return 结果
     */
    BigDecimal selectAmount();

    /**
     * 查询本月押金交付次数
     *
     * @return 结果
     */
    Integer selectRentStartByMonth();

    /**
     * 近十天收入统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectLastRentPayment();
}
