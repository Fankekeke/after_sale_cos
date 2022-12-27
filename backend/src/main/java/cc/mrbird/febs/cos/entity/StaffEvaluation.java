package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 评价管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StaffEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 客户ID
     */
    private Integer userId;

    /**
     * 工单编号
     */
    private String orderCode;

    /**
     * 所属员工
     */
    private Integer staffId;

    /**
     * 准时得分
     */
    private BigDecimal scheduleScore;

    /**
     * 维修得分
     */
    private BigDecimal repairScore;

    /**
     * 服务得分
     */
    private BigDecimal serviceScore;

    /**
     * 综合得分
     */
    private BigDecimal score;

    /**
     * 评价时间
     */
    private String createDate;

    @TableField(exist = false)
    private String staffName;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String orderName;
}
