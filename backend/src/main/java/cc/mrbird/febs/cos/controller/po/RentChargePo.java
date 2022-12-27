package cc.mrbird.febs.cos.controller.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class RentChargePo implements Serializable {

    /**
     * 员工编号
     */
    private String staffCode;

    /**
     * 状态（1.在售 2.已售 3.下架）
     */
    private Integer planStatus;

    /**
     * 小区编号
     */
    private String communityCode;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 房屋编号
     */
    private String houseCode;

    /**
     * 所属商圈
     */
    private String businessDistrict;

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
}
