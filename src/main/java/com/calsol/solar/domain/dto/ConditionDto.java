package com.calsol.solar.domain.dto;

import com.calsol.solar.domain.IDevice;
import com.calsol.solar.domain.entity.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Condition dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionDto implements IDevice {


    private String nameDesign;
    private Double powerArea;
    private Double autonomy;

    /**
     * Gets condition.
     *
     * @return the condition
     */
    public Condition getCondition() {

        return Condition.builder().powerArea(powerArea).demandedAutonomy(autonomy).build();
    }
}
