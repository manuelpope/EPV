package com.calsol.solar.controller;

import com.calsol.solar.domain.entity.Panel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The type Panel dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PanelDto {

    private final static List<Integer> ALLOWED_WATTS = new ArrayList<>(Arrays.asList(230, 250, 500));
    private final static List<Double> ALLOWED_Volts = new ArrayList<>(Arrays.asList(24.0, 48.0));
    private String nameDesign;
    private Integer wattsPk;
    private Double voltage;

    /**
     * Gets condition.
     *
     * @return the condition
     * @throws Exception the exception
     */
    public Panel getPanel() throws Exception {

        Optional.ofNullable(wattsPk).filter(s -> ALLOWED_WATTS.contains(wattsPk)).orElseThrow(() -> new Exception("Not valid wattPk value"));
        Optional.ofNullable(voltage).filter(s -> ALLOWED_Volts.contains(voltage)).orElseThrow(() -> new Exception("Not valid Voltage value"));

        return Panel.builder().wattsPk(wattsPk).voltage(voltage).build();
    }
}
