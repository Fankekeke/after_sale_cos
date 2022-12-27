package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 员工服务评价
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StaffEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工编号
     */
    private String staffCode;

    /**
     * 出租ID
     */
    private Integer rentId;

    /**
     * 服务得分
     */
    private BigDecimal serviceScore;

    /**
     * 评价人
     */
    private String userCode;

    /**
     * 评价时间
     */
    private String createDate;

    public StaffEvaluation(String staffCode, Integer rentId, BigDecimal serviceScore, String userCode, String createDate) {
        this.staffCode = staffCode;
        this.rentId = rentId;
        this.serviceScore = serviceScore;
        this.userCode = userCode;
        this.createDate = createDate;
    }

    public StaffEvaluation() {}
}
