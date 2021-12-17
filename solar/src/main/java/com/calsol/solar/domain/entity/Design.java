package com.calsol.solar.domain.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Design.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"condition", "panel", "sizingDesign"})
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_sizing")
    private SizingDesign sizingDesign;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_design_id")
    private List<Load> loadList = new ArrayList<>();

    /**
     * Sets condition.
     *
     * @param condition the condition
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     * Sets panel.
     *
     * @param panel the panel
     */
    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    /**
     * Sets load list.
     *
     * @param loadList the load list
     */
    public void setLoadList(List<Load> loadList) {
        if (this.loadList == null) {
            this.loadList = new ArrayList<>();
        }

        this.loadList.addAll(loadList);

    }
}


