package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 员工管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StaffInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 员工Code
     */
    private String staffCode;

    /**
     * 员工类型
     */
    private Integer staffType;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 员工照片
     */
    private String avatar;

    /**
     * 等级
     */
    private Integer staffLevel;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 员工状态（0.离职 1.在岗）
     */
    private Integer staffStatus;

    private Integer sysUserId;

    /**
     * 待售出数量
     */
    @TableField(exist = false)
    private Integer waitSellNum;

    /**
     * 已售出数量
     */
    @TableField(exist = false)
    private Integer sellNum;
}
