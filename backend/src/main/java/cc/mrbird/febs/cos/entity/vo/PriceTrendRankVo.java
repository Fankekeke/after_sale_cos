package cc.mrbird.febs.cos.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PriceTrendRankVo implements Serializable {

    private String name;

    private BigDecimal trend;

}
