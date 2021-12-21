package com.calsol.solar.util;

import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.domain.entity.Load;
import com.calsol.solar.domain.entity.SizingDesign;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Utils electrical specialist.
 */
public class UtilsElectricalSpecialist {


    /**
     * The constant DISCHARGE_FACTOR.
     */
    private static final Double DISCHARGE_FACTOR = 0.3;

    /**
     * Sets total day energy.
     *
     * @param sizingDesign the sizing design
     * @param design       the design
     */
    public static void setTotalDayEnergy(SizingDesign sizingDesign, Design design) {

        List<Double> doubles = design.getLoadList().stream().map(Load::getEnergyDay).collect(Collectors.toList());
        Double sum = doubles.stream()
                .reduce(0.0, Double::sum);

        sizingDesign.setAllEnergyDay(sum);
    }

    /**
     * Sets total night energy.
     *
     * @param sizingDesign the sizing design
     * @param design       the design
     */
    public static void setTotalNightEnergy(SizingDesign sizingDesign, Design design) {

        List<Double> doubles = design.getLoadList().stream().map(Load::getEnergyNight).collect(Collectors.toList());
        Double sum = doubles.stream()
                .reduce(0.0, Double::sum);

        sizingDesign.setAllEnergyNight(sum);
    }

    /**
     * Sets all demand energy.
     *
     * @param sizingDesign the sizing design
     */
    public static void setAllDemandEnergy(SizingDesign sizingDesign) {

        sizingDesign.setTotalEnergy(sizingDesign.getAllEnergyDay() + sizingDesign.getAllEnergyNight());
    }


    /**
     * Sets autonomy system.
     *
     * @param s the s
     */
    public static void setAutonomySystem(SizingDesign s) {

        s.setAutonomySystem(s.getAllEnergyNight() / s.getTotalEnergy());
    }

    /**
     * Sets power 110.
     *
     * @param s the s
     * @param d the d
     */
    public static void setPower110(SizingDesign s, Design d) {

        List<Double> doubles = d.getLoadList().stream().map(Load::getPower110VoltsAC).collect(Collectors.toList());
        doubles.add(0.0);
        Double sum = doubles.stream()
                .reduce(0.0, Double::sum);

        s.setAllPower110(sum);
    }

    /**
     * Sets power 12.
     *
     * @param s the s
     * @param d the d
     */
    public static void setPower12(SizingDesign s, Design d) {

        List<Double> doubles = d.getLoadList().stream().map(Load::getPower12VoltsDC).collect(Collectors.toList());
        Double sum = doubles.stream()
                .reduce(0.0, Double::sum);

        s.setAllPower12(sum);
    }

    /**
     * Sets power pv.
     *
     * @param s the s
     * @param d the d
     */
    public static void setPowerPV(SizingDesign s, Design d) {

        s.setPowerPV(s.getTotalEnergy() / (d.getCondition().getPowerArea() * 0.85 * 0.95));

    }

    /**
     * Sets panel quantity.
     *
     * @param s the s
     * @param d the d
     */
    public static void setPanelQuantity(SizingDesign s, Design d) {

        s.setQuantityPanels((int) Math.ceil(s.getPowerPV() / d.getPanel().getWattsPk()));
    }

    /**
     * Sets current storage.
     *
     * @param s the s
     * @param d the d
     */
    public static void setCurrentStorage(SizingDesign s, Design d) {

        s.setCurrentStorage(s.getAllEnergyNight() / (0.85 * 0.95 * d.getPanel().getVoltage()));
    }

    /**
     * Sets batteries quantity.
     *
     * @param s the s
     * @param d the d
     */
    public static void setBatteriesQuantity(SizingDesign s, Design d) {
        s.setCurrentStorageBank(s.getCurrentStorage() / DISCHARGE_FACTOR);
        //2 in series batteries to reach 24 V
        s.setQuantityBatteries(2 * (int) Math.ceil((s.getCurrentStorage() / DISCHARGE_FACTOR) / 100.0));
    }

    /**
     * Sets limits current controller.
     *
     * @param s the s
     * @param d the d
     */
    public static void setLimitsCurrentController(SizingDesign s, Design d) {

        s.setCurrentMAXControllerIn(Math.ceil(d.getPanel().getWattsPk() * s.getQuantityPanels() / d.getPanel().getVoltage()));
        // remember Inverter goes directly connected to bank
        s.setCurrentMAXControllerOut(Math.ceil(s.getAllPower110() / (0.85 * d.getPanel().getVoltage())));

        s.setOutputCurrentBattery12V(Math.ceil(s.getAllPower12() / 12.0));


    }

    /**
     * Sets power inverter vac.
     *
     * @param s the s
     */
    public static void setPowerInverterVAC(SizingDesign s) {

        s.setPowerMinInverter(Math.ceil((s.getAllPower110() * 1.6) / 0.9));
    }

}
