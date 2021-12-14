package com.calsol.solar.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Condition.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Condition implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "FK_DESIGN", updatable = false, nullable = false)
    private Design design;

    private Double powerArea;
    private Double autonomy;


}
