package com.calsol.solar.controller;

import com.calsol.solar.domain.dto.ConditionDto;
import com.calsol.solar.domain.dto.PanelDto;
import com.calsol.solar.domain.dto.SelectionLoadDto;
import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.service.ServiceDesignBuild;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Controller design.
 */
@RestController
@RequestMapping("/v1/calc")
@Slf4j
@Data
public class ControllerDesign {


    @Autowired
    private ServiceDesignBuild serviceDesignBuild;

    /**
     * Instantiates a new Controller design.
     *
     * @param serviceDesignBuild the service design build
     */
    public ControllerDesign(ServiceDesignBuild serviceDesignBuild) {
        this.serviceDesignBuild = serviceDesignBuild;
    }

    /**
     * Put in map response entity.
     *
     * @param designInfo the design info
     * @return the response entity
     */
    @PostMapping("/init")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity putInMap(@RequestBody @Valid Design designInfo) {

        try {
            serviceDesignBuild.addNewDesignContext(designInfo);
            return ResponseEntity.ok(designInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }


    }


    /**
     * Gets all context.
     *
     * @return the all context
     */
    @GetMapping("/context")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllContext() {
        return ResponseEntity.ok(serviceDesignBuild.getEntrySet());
    }


    /**
     * Add condition design response entity.
     *
     * @param conditionDto the condition dto
     * @return the response entity
     */
    @PostMapping("/condition")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity addConditionDesign(@RequestBody @Valid ConditionDto conditionDto) {

        try {
            Design design = serviceDesignBuild.setConditionDesign(conditionDto);
            return ResponseEntity.ok(design);

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


    /**
     * Add panel design response entity.
     *
     * @param panelDto the panel dto
     * @return the response entity
     */
    @PostMapping("/panel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity addPanelDesign(@RequestBody @Valid PanelDto panelDto) {

        try {
            Design design = serviceDesignBuild.setPanelDesign(panelDto);
            return ResponseEntity.ok(design);

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    /**
     * Add loads design response entity.
     *
     * @param selectionLoadDto the selection load dto
     * @return the response entity
     */
    @PostMapping("/loads")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity addLoadsDesign(@RequestBody @Valid SelectionLoadDto selectionLoadDto) {
//TODO validations of double power type , only ac or dc not both
        try {
            Design design = serviceDesignBuild.setLoadDesign(selectionLoadDto);
            return ResponseEntity.ok(design);

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    /**
     * Add loads design response entity.
     *
     * @param nameDesign the name design
     * @return the response entity
     */
    @GetMapping("/save/{nameDesign}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity addLoadsDesign(@PathVariable("nameDesign") String nameDesign) {

        try {
            return ResponseEntity.ok(serviceDesignBuild.saveDesign(nameDesign));

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    /**
     * Sizing design response entity.
     *
     * @param nameDesign the name design
     * @return the response entity
     */
    @GetMapping("/sizing/{nameDesign}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sizingDesign(@PathVariable("nameDesign") String nameDesign) {

        try {
            Design design = serviceDesignBuild.setSizing(nameDesign);
            return ResponseEntity.ok(design);

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}
