package com.supcon.orchid.SESECD.model.vo.map;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PointVO {
    private BigDecimal lon;
    private BigDecimal lat;
    private BigDecimal height;

    public String toString(String split) {
        return lon + split+ lat;
    }
}