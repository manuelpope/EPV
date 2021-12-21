package com.calsol.solar.util;

import com.calsol.solar.domain.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatorElectricalProcessTest {

    private Design design;

    @BeforeEach
    void setUp() {

        design = Design.builder().name("test").localDateTime(LocalDateTime.now(TimeZone.getTimeZone("UTC").toZoneId())).build();
        Condition condition = Condition.builder().demandedAutonomy(0.3).powerArea(3.8).build();
        Panel panel = Panel.builder().voltage(24.0).wattsPk(230).build();
        Load l1 = Load.builder().energyDay(160.0).totalEnergy(160.0)
                .energyNight(0.0).power12VoltsDC(20.0).power110VoltsAC(0.0).type("DC").build();
        Load l2 = Load.builder().energyDay(150.0).energyNight(60.0)
                .totalEnergy(210.0).power110VoltsAC(30.0).power12VoltsDC(0.0).type("AC").build();
        Load l3 = Load.builder().energyDay(0.0).energyNight(200.0)
                .totalEnergy(200.0).power110VoltsAC(190.0).power12VoltsDC(0.0).type("AC").build();
        Load l4 = Load.builder().energyDay(180.0).energyNight(60.0)
                .totalEnergy(240.0).power110VoltsAC(30.0).power12VoltsDC(0.0).type("AC").build();

        design.setPanel(panel);
        design.setCondition(condition);
        design.setLoadList(Arrays.asList(l1, l2, l3, l4));


    }

    @Test
    void buildSizing() {


        SizingDesign s = new SizingDesign();
        CalculatorElectricalProcess.buildSizing(s, design);

        assertEquals(2, s.getQuantityBatteries());
        assertEquals(2, s.getQuantityPanels());

        assertTrue(0.001 > (s.getTotalEnergy() - 810.0));
        assertTrue(0.001 > (s.getAllEnergyNight() - 320.0));
        assertTrue(0.001 > (s.getAllEnergyDay() - 490.0));
        assertTrue(0.001 > (s.getAutonomySystem() - 0.395));
        assertTrue(0.1 > (s.getCurrentMAXControllerIn() - 20));
        assertTrue(0.1 > (s.getCurrentStorage() - 16.5));
        assertTrue(0.1 > (s.getCurrentMAXControllerOut() - 13));
        assertTrue(0.1 > (s.getPowerPV() - 263.97));
        assertTrue(0.1 > (s.getOutputCurrentBattery12V() - 2));
        assertTrue(0.1 > (s.getPowerMinInverter() - 450.0));


        System.out.println(s);

    }
}