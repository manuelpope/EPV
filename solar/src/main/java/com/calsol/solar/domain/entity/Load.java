package com.calsol.solar.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Load ac.
 */
@Entity
@Data
@Builder
public class Load implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Double power110VoltsAC;
    private Double power12VoltsDC;
    private Double energyDay;
    private Double energyNight;
    private Double totalEnergy;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "design_id", nullable = false)
    private Design design;
    private static final long serialVersionUID = 42L;

}
