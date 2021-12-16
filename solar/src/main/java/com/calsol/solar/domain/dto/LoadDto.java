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

    private String type;
    private Double voltage;
    private Double powerDC;
    private Integer quantity;
    private Double workingDayHours;
    private Double workingNightHours;

}
