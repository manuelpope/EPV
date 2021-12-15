package com.calsol.solar.controller;

import com.calsol.solar.domain.dto.ConditionDto;
import com.calsol.solar.domain.dto.LoadDto;
import com.calsol.solar.domain.dto.PanelDto;
import com.calsol.solar.domain.dto.SelectionLoadDto;
import com.calsol.solar.domain.entity.Condition;
import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.domain.entity.Load;
import com.calsol.solar.domain.entity.Panel;
import com.calsol.solar.repository.dao.IRepositoryDesign;
import com.calsol.solar.service.ContextDesign;
import com.calsol.solar.service.ILoadService;
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


/**
 * The type Controller design.
 */
@RestController
@RequestMapping("/v1/calc")
@Slf4j
public class ControllerDesign {

    private final ZoneId zoneId;
    @Autowired
    private final IRepositoryDesign repositoryDesign;
    @Autowired
    private ContextDesign contextDesign;
    @Autowired
    private ILoadService loadService;


    /**
     * Instantiates a new Controller design.
     *
     * @param IRepositoryDesign the repository design
     */
    public ControllerDesign(IRepositoryDesign IRepositoryDesign) {
        this.repositoryDesign = IRepositoryDesign;
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
            Design design = contextDesign.getDesign(conditionDto.getNameDesign());
            Condition condition = conditionDto.getCondition();
            design.setCondition(condition);
            contextDesign.update(design);

            return ResponseEntity.ok(design);

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

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

    @PostMapping("/loads")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity addLoadsDesign(@RequestBody @Valid SelectionLoadDto selectionLoadDto) {

        try {
            Design design = contextDesign.getDesign(selectionLoadDto.getNameDesign());
            loadService.loadDtoList(selectionLoadDto.getLoadDtoList());
            List<Load> loadList = loadService.buildLoads();
            design.setLoadList(loadList);
            contextDesign.update(design);


            return ResponseEntity.ok(repositoryDesign.save(design));

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}
