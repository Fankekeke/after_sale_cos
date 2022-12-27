package cc.mrbird.febs.cos.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ContractVo implements Serializable {

    /**
     * 租房用户
     */
    private String rentUserName;

    /**
     * 房屋所属业主
     */
    private String ownerUserName;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 房间地址
     */
    private String houseAddress;

    /**
     * 居住时间（月）
     */
    private Integer rentDay;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 开始居住时间
     */
    private String startLive;

    /**
     * 居住结束时间
     */
    private String endLive;

    /**
     * 缴费方式（1.押一付一 2.押一付三）
     */
    private String payType;

    /**
     * 合同金额
     */
    private BigDecimal contractPrice;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 合同编号
     */
    private String contractCode;

    private String city;

    private String area;

    private String province;
}
