package com.calsol.solar.service;

import com.calsol.solar.domain.entity.Design;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Context design.
 */
@Data
@Service
@Slf4j
public class ContextDesign {

    private Map<String, Design> context;

    /**
     * Instantiates a new Context design.
     */
    public ContextDesign() {
        context = new HashMap<>();
    }

    /**
     * Gets design.
     *
     * @param nameDesign the name design
     * @return the design
     */
    public Design getDesign(String nameDesign) {

        return context.get(nameDesign);

    }

    /**
     * Add design.
     *
     * @param design the design
     * @throws Exception the exception
     */
    public void addDesign(Design design) throws Exception {
        log.info("Adding " + design.getName());
        if (context.containsKey(design.getName())) {
            throw new Exception("its already set that name");
        }
        context.put(design.getName(), design);

    }
}
