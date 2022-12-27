package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息发送记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 模板ID
     */
    private Integer templateId;

    /**
     * 发送用户类型（1.用户 2.员工）
     */
    private String sendUserType;

    /**
     * 接收人编号
     */
    private String sendAccount;

    /**
     * 发送时间
     */
    private String createDate;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * email
     */
    private String email;

    /**
     * avatar
     */
    private String avatar;


}
