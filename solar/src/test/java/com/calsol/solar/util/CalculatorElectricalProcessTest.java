package com.calsol.solar.util;

import com.calsol.solar.domain.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.TimeZone;

class CalculatorElectricalProcessTest {

    private Design design;

    @BeforeEach
    void setUp() throws IOException {

        design = Design.builder().name("test").localDateTime(LocalDateTime.now(TimeZone.getTimeZone("UTC").toZoneId())).build();
        Condition condition = Condition.builder().demandedAutonomy(0.3).powerArea(3.8).build();
        Panel panel = Panel.builder().voltage(24.0).wattsPk(230).build();
        //TODO check logic of sizing , first make and spreadsheet to have this design
        Load l1 = Load.builder().energyDay(160.0).totalEnergy(160.0).energyNight(0.0).power12VoltsDC(20.0).power110VoltsAC(0.0).type("DC").build();
        Load l2 = Load.builder().energyDay(150.0).energyNight(60.0).totalEnergy(210.0).power110VoltsAC(30.0).power12VoltsDC(0.0).type("AC").build();
        Load l3 = Load.builder().energyDay(0.0).energyNight(200.0).totalEnergy(200.0).power110VoltsAC(190.0).power12VoltsDC(0.0).type("AC").build();
        Load l4 = Load.builder().energyDay(180.0).energyNight(60.0).totalEnergy(240.0).power110VoltsAC(30.0).power12VoltsDC(0.0).type("AC").build();

        design.setPanel(panel);
        design.setCondition(condition);
        design.setLoadList(Arrays.asList(l1, l2, l3, l4));


    }

    @Test
    void buildSizing() {
        SizingDesign s = new SizingDesign();
        CalculatorElectricalProcess.buildSizing(s, design);
        System.out.println(s);
    }
}