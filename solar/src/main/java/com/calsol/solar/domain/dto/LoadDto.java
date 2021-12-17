package com.calsol.solar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Load dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadDto {
//TODO Mono change name of powerDC and check the whole flow
    private String type;
    private Double voltage;
    private Double power;
    private Integer quantity;
    private Double workingDayHours;
    private Double workingNightHours;

}
