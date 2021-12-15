package com.calsol.solar.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Load ac.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Load implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Double power110VoltsAC;
    private Double power12VoltsDC;
    private Double energyDay;
    private Double energyNight;
    private Double totalEnergy;
    private String type;
    private static final long serialVersionUID = 42L;

}
