package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 预约管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReserveInfo implements Serializable {

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
     * 备注
     */
    private String remark;

    /**
     * 图片
     */
    private String images;

    /**
     * 售后产品
     */
    private Integer productId;

    /**
     * 预约时间
     */
    private String reserveDate;

    /**
     * 创建时间
     */
    private String createDate;

    private String orderCode;

    /**
     * 开关状态（0.开启 1.已受理 2.退回）
     */
    private Integer openFlag;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String productName;

    @TableField(exist = false)
    private Integer productType;

}
