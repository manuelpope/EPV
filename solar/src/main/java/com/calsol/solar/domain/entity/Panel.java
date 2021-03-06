package com.calsol.solar.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Panel.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode
public class Panel implements Serializable {

    private static final long serialVersionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer wattsPk;
    private Double voltage;

}
