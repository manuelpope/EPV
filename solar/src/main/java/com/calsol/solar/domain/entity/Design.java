package com.calsol.solar.domain.entity;


import lombok.*;

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
@EqualsAndHashCode(exclude = {"condition", "panel"})
@Builder
public class Design implements Serializable {

    private static final long serialVersionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty
    @Column(unique = true, nullable = false)
    @NotNull
    private String name;
    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime localDateTime;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_condition")
    private Condition condition;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_panel")
    private Panel panel;

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }
}


