package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfo implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String code;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 性别 1.男 2.女
     */
    private Integer sex;

    /**
     * 用户类型 1.普通用户 2.业主
     */
    private Integer type;

    /**
     * 创建时间
     */
    private String createDate;

    private Integer sysUserId;

}
