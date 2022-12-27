package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 小区管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommunityInfo implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID = 1L;

    /**
     * 小区编号
     */
    private String code;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 物业类型（1.公寓住宅）
     */
    private Integer propertyType;

    /**
     * 权属类别（1.商品房住宅）
     */
    private Integer tenureCategory;

    /**
     * 竣工时间
     */
    private String completionTime;

    /**
     * 产权年限
     */
    private String propertyTenure;

    /**
     * 总户数
     */
    private Integer totalHouses;

    /**
     * 总建面积
     */
    private BigDecimal totalConstructionArea;

    /**
     * 容积率
     */
    private BigDecimal volumeRate;

    /**
     * 绿化率
     */
    private BigDecimal greeningRate;

    /**
     * 建筑类型（1.小高层）
     */
    private Integer buildingType;

    /**
     * 所属商圈
     */
    private String businessDistrict;

    /**
     * 统一供暖（1.是 2.否）
     */
    private Integer unifiedHeating;

    /**
     * 供水供电（1.民用 2.商用）
     */
    private Integer waterSupply;

    /**
     * 停车位
     */
    private String parkingSpace;

    /**
     * 物业费
     */
    private String propertyCosts;

    /**
     * 停车费
     */
    private String parkingFee;

    /**
     * 车位管理费
     */
    private String parkingManagementFee;

    /**
     * 物业公司
     */
    private String propertyCompany;

    /**
     * 小区地址
     */
    private String address;

    /**
     * 开发商
     */
    private String developer;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private String createDate;

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
     * 备注
     */
    private String remark;
}
