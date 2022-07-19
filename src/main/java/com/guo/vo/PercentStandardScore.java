package com.guo.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PercentStandardScore {


    /**
     * 原始分
     */
    private BigDecimal score;

    /**
     * 升序标准百分
     */
    private BigDecimal asePercent;


    /**
     * 降序标准百分
     */
    private BigDecimal desPercent;


}
