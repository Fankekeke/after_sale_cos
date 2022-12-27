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
 * 交付审核
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeliveryReview implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 交管ID
     */
    private Integer chargeId;

    /**
     * 员工编号
     */
    private String staffCode;

    /**
     * 租房用户
     */
    private String rentUserCode;

    /**
     * 房屋所属业主
     */
    private String ownerUserCode;

    /**
     * 当前流程状态（1.等待审批 2. 通过 3.驳回）
     */
    private String step;

    /**
     * 房屋编号
     */
    private String houseCode;

    /**
     * 居住时间（月）
     */
    private Integer rentDay;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 开始居住时间
     */
    private String startLive;

    /**
     * 居住结束时间
     */
    private String endLive;

    /**
     * 缴费方式（1.押一付一 2.押一付三）
     */
    private String payType;

    /**
     * 合同金额
     */
    private BigDecimal contractPrice;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 合同状态（1.正常 2.中止）
     */
    private String contractStatus;

    @TableField(exist = false)
    private String staffName;

    @TableField(exist = false)
    private String rentUserName;

}
