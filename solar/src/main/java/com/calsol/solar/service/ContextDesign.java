package com.calsol.solar.service;

import com.calsol.solar.domain.entity.Design;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Data
@Service
@Slf4j
public class ContextDesign {

    private Map<String, Design> context;

    public ContextDesign() {
        context = new HashMap<>();
    }

    public Design getDesign(String nameDesign) {

        return context.get(nameDesign);

    }

    public void addDesign(Design design) {
        log.info("Adding " + design.getName());
        context.put(design.getName(), design);

    }
}
