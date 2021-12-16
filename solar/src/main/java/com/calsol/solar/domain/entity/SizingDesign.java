package com.calsol.solar.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * The type Sizing design.
 */
@Data
@NoArgsConstructor
@ToString
@Entity
@EqualsAndHashCode
public class SizingDesign {


    private Integer quantityPanels;

    private Integer quantityBatteries;

    private Double totalEnergy;

    private Double allEnergyDay;

    private Double allEnergyNight;

    private Double allPower110;

    private Double allPower12;

    private Double powerPV;

    private Double currentStorage;

    private Double currentMAXControllerIn;

    private Double currentMAXControllerOut;

    private Double currentStorageBank;

    private Double outputCurrentBattery12V;

    private Double powerMinInverter;

    private Double autonomySystem;


}
