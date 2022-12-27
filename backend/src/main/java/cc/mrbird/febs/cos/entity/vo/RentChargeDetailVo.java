package cc.mrbird.febs.cos.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RentChargeDetailVo implements Serializable {

    private String rentId;

    /**
     * 交管ID
     */
    private Integer chargeId;

    /**
     * 员工编号
     */
    private String staffCode;

    /**
     * 租房用户
     */
    private String rentUserCode;

    /**
     * 房屋所属业主
     */
    private String ownerUserCode;

    /**
     * 房屋编号
     */
    private String houseCode;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 小区名称
     */
    private String communityName;

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
     * 房租/月
     */
    private BigDecimal contractPrice;
}
