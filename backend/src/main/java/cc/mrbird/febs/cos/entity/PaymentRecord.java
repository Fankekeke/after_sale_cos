package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 缴费记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PaymentRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 缴费人
     */
    private String rentUserCode;

    /**
     * 缴费金额
     */
    private BigDecimal amount;

    /**
     * 可供时间（月）
     */
    private Integer rentDay;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 缴费类型（1.房租 2.押金）
     */
    private String paymentType;

    /**
     * 缴费时间
     */
    private String createDate;

    @TableField(exist = false)
    private String rentUser;

}
