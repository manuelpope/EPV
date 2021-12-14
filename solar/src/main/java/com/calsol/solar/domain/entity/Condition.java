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
@EqualsAndHashCode(exclude = "design")
public class Condition implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Double powerArea;
    private Double autonomy;

    private static final long serialVersionUID = 42L;
}
