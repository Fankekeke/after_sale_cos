package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 报修中心
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RepairInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 报修单编号
     */
    private String repairCode;

    /**
     * 选择员工
     */
    private Integer staffId;

    /**
     * 维修状态（0.带接收 1.正在检测问题 2.维修中 3.维修完成 4.异常退回）
     */
    private Integer repairStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String orderName;

    @TableField(exist = false)
    private String staffName;

    @TableField(exist = false)
    private Long userId;

    @TableField(exist = false)
    private Long clientId;
}
