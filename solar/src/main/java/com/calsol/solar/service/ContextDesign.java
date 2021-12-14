package com.calsol.solar.service;

import com.calsol.solar.domain.entity.Design;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        context = new ConcurrentHashMap<>();
    }

    /**
     * Gets design.
     *
     * @param nameDesign the name design
     * @return the design
     */
    public Design getDesign(String nameDesign) throws Exception {
        assertExistenceDesign(nameDesign);
        return context.get(nameDesign);

    }

    private void assertExistenceDesign(String nameDesign) throws Exception {
        if (!context.containsKey(nameDesign)) {
            throw new Exception("There is no design with that name");
        }
    }

    /**
     * Add design.
     *
     * @param design the design
     * @throws Exception the exception
     */
    public synchronized void addDesign(Design design) throws Exception {
        log.info("Adding " + design.getName());
        if (context.containsKey(design.getName())) {
            throw new Exception("its already set that name");
        }
        context.put(design.getName(), design);

    }

    /**
     * Update.
     *
     * @param design the design
     * @throws Exception the exception
     */
    public synchronized void update(Design design) throws Exception {
        log.info("update " + design.getName());
        assertExistenceDesign(design.getName());
        context.replace(design.getName(), design);

    }
}
