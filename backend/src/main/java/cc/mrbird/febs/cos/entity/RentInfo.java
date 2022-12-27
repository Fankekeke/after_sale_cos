package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 出租信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RentInfo implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID = 1L;

    /**
     * 出租标题
     */
    private String title;

    /**
     * 房屋编号
     */
    private String houseCode;

    /**
     * 房间类型（1.主卧 2.次卧）
     */
    private Integer roomType;

    /**
     * 租金每月
     */
    private BigDecimal rentPrice;

    /**
     * 租金备注
     */
    private String rentRemark;

    /**
     * 出租要求
     */
    private String rentalRequest;

    /**
     * 房间图片
     */
    private String roomPictures;

    /**
     * 电视（1.有 2.无）
     */
    private Integer hasTelevision;

    /**
     * 空调（1.有 2.无）
     */
    private Integer hasAirConditioner;

    /**
     * 衣柜（1.有 2.无）
     */
    private Integer hasWardrobe;

    /**
     * 床（1.有 2.无）
     */
    private Integer hasBed;

    /**
     * 卫生间（1.有 2.无）
     */
    private Integer hasBathroom;

    /**
     * 智能门锁（1.有 2.无）
     */
    private Integer hasDoorLock;

    /**
     * 阳台（1.有 2.无）
     */
    private Integer hasBalcony;

    /**
     * 暖气（1.有 2.无）
     */
    private Integer hasHeating;

    /**
     * 冰箱（1.有 2.无）
     */
    private Integer hasRefrigerator;

    /**
     * 洗衣机（1.有 2.无）
     */
    private Integer hasWashingMachine;

    /**
     * 热水器（1.有 2.无）
     */
    private Integer hasWaterHeater;

    /**
     * 宽带（1.有 2.无）
     */
    private Integer hasBroadband;

    /**
     * 沙发（1.有 2.无）
     */
    private Integer hasSofa;

    /**
     * 油烟机（1.有 2.无）
     */
    private Integer hasRangeHood;

    /**
     * 燃气灶（1.有 2.无）
     */
    private Integer hasGasStoves;

    /**
     * 可做饭（1.有 2.无）
     */
    private Integer hasCook;

    /**
     * 删除标识（1.上架 2.下架 3.已被出租）
     */
    private Integer flag;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 访问量
     */
    private Integer views;

    /**
     * 合租类型（1.整租 2.合租）
     */
    private Integer rentType;

    @TableField(exist = false)
    private Integer userId;
}
