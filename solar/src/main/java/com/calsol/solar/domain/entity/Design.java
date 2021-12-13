package com.calsol.solar.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Design.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Design implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty
    @Column(unique = true, nullable = false)
    @NotNull
    private String name;
    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime localDateTime;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "design")
    private Condition condition;


}


