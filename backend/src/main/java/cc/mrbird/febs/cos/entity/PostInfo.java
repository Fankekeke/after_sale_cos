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
 * 发帖记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostInfo implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID = 1L;

    /**
     * 发帖人
     */
    private String userCode;

    /**
     * 标题
     */
    private String title;

    /**
     * 帖子类型（1.小区 2.租房 3.出售）
     */
    private Integer postType;

    /**
     * 所属编号
     */
    private String nodeCode;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 图片
     */
    private String picture;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String userName;

}
