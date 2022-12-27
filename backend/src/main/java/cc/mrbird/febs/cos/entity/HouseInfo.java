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
 * 房屋信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HouseInfo implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID = 1L;

    /**
     * 房屋编号
     */
    private String code;

    /**
     * 房屋地址
     */
    private String address;

    /**
     * 所属省
     */
    private String province;

    /**
     * 所属市
     */
    private String city;

    /**
     * 所属区域
     */
    private String area;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 室内图
     */
    private String indoorMap;

    /**
     * 户型图
     */
    private String housePlan;

    /**
     * 环境图
     */
    private String environmentMap;

    /**
     * 房间数量
     */
    private Integer roomNumber;

    /**
     * 客厅数量
     */
    private Integer livingRoomNumber;

    /**
     * 卫生间数量
     */
    private Integer bathroomNumber;

    /**
     * 房间面积
     */
    private BigDecimal roomSize;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 装修类型（1.精装修 2.普通装修 3.暂无装修）
     */
    private Integer decorationType;

    /**
     * 房屋类型 （1.普通住宅 2.高层楼 3.别墅 4.大平层）
     */
    private Integer houseType;

    /**
     * 朝向
     */
    private String towards;

    /**
     * 房源概况
     */
    private String propertyOverview;

    /**
     * 标签
     */
    private String tags;

    /**
     * 所属小区
     */
    private String communityCode;

    /**
     * 所属业主
     */
    private String ownerCode;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 小区名称
     */
    @TableField(exist = false)
    private String communityName;
}
