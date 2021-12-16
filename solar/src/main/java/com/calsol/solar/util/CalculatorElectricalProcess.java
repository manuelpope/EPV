package com.calsol.solar.util;

import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.domain.entity.SizingDesign;

/**
 * The type Calculator electrical process.
 */
public class CalculatorElectricalProcess extends UtilsElectricalSpecialist {


    /**
     * Build sizing.
     *
     * @param s the s
     * @param d the d
     */
    public static void buildSizing(SizingDesign s, Design d) {

        setTotalDayEnergy(s, d);
        setTotalNightEnergy(s, d);
        setAllDemandEnergy(s);
        setAutonomySystem(s);
        setPower110(s, d);
        setPower12(s, d);
        setPowerPV(s, d);
        setPanelQuantity(s, d);
        setCurrentStorage(s, d);
        setBatteriesQuantity(s, d);
        setLimitsCurrentController(s, d);
        setPowerInverterVAC(s);
    }
}
