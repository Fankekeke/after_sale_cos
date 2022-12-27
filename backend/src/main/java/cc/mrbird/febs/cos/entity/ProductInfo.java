package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 产品管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品编号
     */
    private String code;

    /**
     * 产品分类
     */
    private Integer type;

    /**
     * 产品图片
     */
    private String images;

    /**
     * 型号
     */
    private String model;

    /**
     * 创建时间
     */
    private String createDate;


}
