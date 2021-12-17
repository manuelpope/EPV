package com.calsol.solar.util;

import com.calsol.solar.domain.entity.*;
import com.google.gson.Gson;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorElectricalProcessTest {

    private Design design;

    @BeforeEach
    void setUp() throws IOException {

        design = Design.builder().name("test").localDateTime(LocalDateTime.now(TimeZone.getTimeZone("UTC").toZoneId())).build();
        Condition condition = Condition.builder().demandedAutonomy(0.3).powerArea(3.8).build();
        Panel panel = Panel.builder().voltage(24.0).wattsPk(230).build();

        Load l1 = Load.builder().energyDay(160.0).totalEnergy(160.0).energyNight(0.0).power12VoltsDC(20.0).power110VoltsAC(0.0).type("DC").build();
        Load l2 = Load.builder().energyDay(0.0).energyNight(60.0).totalEnergy(60.0).power110VoltsAC(30.0).power12VoltsDC(0.0).type("AC").build();

        design.setPanel(panel);
        design.setCondition(condition);
        design.setLoadList(Arrays.asList(l1, l2));


    }

    @Test
    void buildSizing() {
        SizingDesign s = new SizingDesign();
        CalculatorElectricalProcess.buildSizing(s, design);
        System.out.println(s);
    }
}