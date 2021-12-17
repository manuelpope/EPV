package com.calsol.solar.controller;

import com.calsol.solar.domain.dto.ConditionDto;
import com.calsol.solar.domain.dto.PanelDto;
import com.calsol.solar.domain.dto.SelectionLoadDto;
import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.domain.entity.Load;
import com.calsol.solar.domain.entity.Panel;
import com.calsol.solar.domain.entity.SizingDesign;
import com.calsol.solar.repository.dao.IRepositoryDesign;
import com.calsol.solar.service.ContextDesign;
import com.calsol.solar.service.ILoadService;
import com.calsol.solar.service.ServiceDesignBuild;
import com.calsol.solar.util.CalculatorElectricalProcess;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

//Todo separate logic from controller, moving to service design build mono
/**
 * The type Controller design.
 */
@RestController
@RequestMapping("/v1/calc")
@Slf4j
@Data
public class ControllerDesign {

    private final ZoneId zoneId;
    @Autowired
    private final IRepositoryDesign repositoryDesign;
    @Autowired
    private ContextDesign contextDesign;
    @Autowired
    private ILoadService loadService;
    @Autowired
    private ServiceDesignBuild serviceDesignBuild;

    /**
     * Instantiates a new Controller design.
     *
     * @param IRepositoryDesign  the repository design
     * @param contextDesign
     * @param loadService
     * @param serviceDesignBuild
     */
    public ControllerDesign(IRepositoryDesign IRepositoryDesign, ContextDesign contextDesign, ILoadService loadService, ServiceDesignBuild serviceDesignBuild) {
        this.repositoryDesign = IRepositoryDesign;
        this.contextDesign = contextDesign;
        this.loadService = loadService;
        this.serviceDesignBuild = serviceDesignBuild;
        this.zoneId = TimeZone.getTimeZone("UTC").toZoneId();
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
            designInfo.setLocalDateTime(LocalDateTime.now(zoneId));
            contextDesign.addDesign(designInfo);
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

        return ResponseEntity.ok(contextDesign.getContext().entrySet());


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
            Design design = contextDesign.getDesign(panelDto.getNameDesign());
            Panel panel = panelDto.getPanel();
            design.setPanel(panel);
            contextDesign.update(design);

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

        try {
            Design design = contextDesign.getDesign(selectionLoadDto.getNameDesign());
            loadService.loadDtoList(selectionLoadDto.getLoadDtoList());
            List<Load> loadList = loadService.buildLoads();
            design.setLoadList(loadList);
            contextDesign.update(design);

            // repositoryDesign.save(design);
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
    @GetMapping("/submit/{nameDesign}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity addLoadsDesign(@PathVariable("nameDesign") String nameDesign) {

        try {
            Design design = contextDesign.getDesign(nameDesign);
            SizingDesign sizingDesign = new SizingDesign();
            CalculatorElectricalProcess.buildSizing(sizingDesign, design);
            design.setSizingDesign(sizingDesign);

            // repositoryDesign.save(design);
            return ResponseEntity.ok(design);

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}
