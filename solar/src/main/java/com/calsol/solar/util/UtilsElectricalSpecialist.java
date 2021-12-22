package com.calsol.solar.util;

import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.domain.entity.Load;
import com.calsol.solar.domain.entity.SizingDesign;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Utils electrical specialist.
 */
public class UtilsElectricalSpecialist {


    /**
     * The constant OVER_SCALE_DESIGN_FACTOR.
     */
    public static final double OVER_SCALE_DESIGN_FACTOR = 1.6;
    /**
     * The constant POWER_FACTOR_LOAD.
     */
    public static final double POWER_FACTOR_LOAD = 0.9;
    /**
     * The constant EFFICIENCY_CONTROLLER.
     */
    public static final double EFFICIENCY_CONTROLLER = 0.85;
    /**
     * The constant VOLTAGE_SINGLE_BATTERY.
     */
    public static final double VOLTAGE_SINGLE_BATTERY = 12.0;
    /**
     * The constant CURRENT_STORE_BATTERY_COLUMN.
     */
    public static final double CURRENT_STORE_BATTERY_COLUMN = 100.0;
    /**
     * The constant EFFICIENCY_INVERTER.
     */
    public static final double EFFICIENCY_INVERTER = 0.95;
    /**
     * The constant DISCHARGE_FACTOR.
     */
    private static final Double DISCHARGE_FACTOR = 0.3;
    /**
     * The Rounding 2 decimals.
     */
    public static Function<Double, Double> rounding2Decimals = s -> Math.round(s * 100.0) / 100.0;

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

        sizingDesign.setAllEnergyDay(rounding2Decimals.apply(sum));
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

        sizingDesign.setAllEnergyNight(rounding2Decimals.apply(sum));
    }

    /**
     * Sets all demand energy.
     *
     * @param sizingDesign the sizing design
     */
    public static void setAllDemandEnergy(SizingDesign sizingDesign) {

        sizingDesign.setTotalEnergy(rounding2Decimals.apply(sizingDesign.getAllEnergyDay() + sizingDesign.getAllEnergyNight()));
    }

    /**
     * Sets autonomy system.
     *
     * @param s the s
     */
    public static void setAutonomySystem(SizingDesign s) {

        s.setAutonomySystem(rounding2Decimals.apply(s.getAllEnergyNight() / s.getTotalEnergy()));
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

        s.setAllPower110(rounding2Decimals.apply(sum));
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

        s.setAllPower12(rounding2Decimals.apply(sum));
    }

    /**
     * Sets power pv.
     *
     * @param s the s
     * @param d the d
     */
    public static void setPowerPV(SizingDesign s, Design d) {

        s.setPowerPV(rounding2Decimals.apply(s.getTotalEnergy() / (d.getCondition().getPowerArea() * EFFICIENCY_CONTROLLER * EFFICIENCY_INVERTER)));

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

        s.setCurrentStorage(rounding2Decimals.apply(s.getAllEnergyNight() / (EFFICIENCY_CONTROLLER * EFFICIENCY_INVERTER * d.getPanel().getVoltage())));
    }

    /**
     * Sets batteries quantity.
     *
     * @param s the s
     */
    public static void setBatteriesQuantity(SizingDesign s) {
        s.setCurrentStorageBank(rounding2Decimals.apply(s.getCurrentStorage() / DISCHARGE_FACTOR));
        //2 in series batteries to reach 24 V
        s.setQuantityBatteries(2 * (int) Math.ceil((s.getCurrentStorage() / DISCHARGE_FACTOR) / CURRENT_STORE_BATTERY_COLUMN));
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
        s.setCurrentMAXControllerOut(Math.ceil(s.getAllPower110() / (EFFICIENCY_CONTROLLER * d.getPanel().getVoltage())));

        s.setOutputCurrentBattery12V(Math.ceil(s.getAllPower12() / VOLTAGE_SINGLE_BATTERY));


    }

    /**
     * Sets power inverter vac.
     *
     * @param s the s
     */
    public static void setPowerInverterVAC(SizingDesign s) {

        s.setPowerMinInverter(Math.ceil((s.getAllPower110() * OVER_SCALE_DESIGN_FACTOR) / POWER_FACTOR_LOAD));
    }

}
