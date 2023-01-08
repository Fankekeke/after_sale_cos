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
 * 工单中心
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 关联报修单号
     */
    private String repairCode;

    /**
     * 工单编号
     */
    private String orderCode;

    /**
     * 服务类型
     */
    private Integer serverType;

    /**
     * 本次服务费
     */
    private BigDecimal money;

    /**
     * 备注
     */
    private String remark;

    /**
     * 工单图片
     */
    private String images;

    /**
     * 工单状态（0.正在对应 1.已派发 2.缴费 3.正在维修 4.维修完成 5.已退换 6.完成 7.驳回）
     */
    private Integer status;

    /**
     * 客户
     */
    private Integer customerId;

    /**
     * 客户产品
     */
    private Integer productId;

    /**
     * 选择维修工
     */
    private Integer staffId;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private Long userId;

}
