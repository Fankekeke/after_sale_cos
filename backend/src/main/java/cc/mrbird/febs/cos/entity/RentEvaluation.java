package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 房屋出租评价
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RentEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出租ID
     */
    private Integer rentId;

    /**
     * 房屋编号
     */
    private String houseCode;

    /**
     * 环境得分
     */
    private BigDecimal environmentScore;

    /**
     * 周边设施得分
     */
    private BigDecimal facilityScore;

    /**
     * 房间设备得分
     */
    private BigDecimal deviceScore;

    /**
     * 交通出行得分
     */
    private BigDecimal trafficScore;

    /**
     * 环境噪音得分
     */
    private BigDecimal noiseScore;

    /**
     * 租金得分
     */
    private BigDecimal priceScore;

    /**
     * 综合得分
     */
    private BigDecimal overallScore;

    /**
     * 评价人
     */
    private String userCode;

    /**
     * 房屋出租评价
     */
    private String createDate;

    public RentEvaluation(Integer rentId, String houseCode, BigDecimal environmentScore, BigDecimal facilityScore, BigDecimal deviceScore, BigDecimal trafficScore, BigDecimal noiseScore, BigDecimal priceScore, BigDecimal overallScore, String userCode, String createDate) {
        this.rentId = rentId;
        this.houseCode = houseCode;
        this.environmentScore = environmentScore;
        this.facilityScore = facilityScore;
        this.deviceScore = deviceScore;
        this.trafficScore = trafficScore;
        this.noiseScore = noiseScore;
        this.priceScore = priceScore;
        this.overallScore = overallScore;
        this.userCode = userCode;
        this.createDate = createDate;
    }

    public RentEvaluation() {}
}
