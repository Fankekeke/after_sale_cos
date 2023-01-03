package cc.mrbird.febs.cos.entity.vo;

import cc.mrbird.febs.cos.entity.RepairInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 维修信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RepairVo extends RepairInfo implements Serializable {

    private String orderName;

    private String orderCode;

    private String images;

    private String productName;

    private Integer productTypeName;

    private Integer serverTypeName;
}
