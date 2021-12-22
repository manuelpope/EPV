package com.calsol.solar.service;

import com.calsol.solar.domain.dto.ConditionDto;
import com.calsol.solar.domain.dto.PanelDto;
import com.calsol.solar.domain.dto.SelectionLoadDto;
import com.calsol.solar.domain.entity.*;
import com.calsol.solar.repository.dao.IRepositoryDesign;
import com.calsol.solar.util.CalculatorElectricalProcess;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

/**
 * The type Service design build.
 */
@Service
@NoArgsConstructor
public class ServiceDesignBuild {
    private final ZoneId zoneId = TimeZone.getTimeZone("UTC").toZoneId();
    @Autowired
    private ContextDesign contextDesign;
    @Autowired
    private ILoadService loadService;
    @Autowired
    private IRepositoryDesign repositoryDesign;

    /**
     * Instantiates a new Service design build.
     *
     * @param contextDesign    the context design
     * @param loadService      the load service
     * @param repositoryDesign the repository design
     */
    public ServiceDesignBuild(ContextDesign contextDesign, ILoadService loadService, IRepositoryDesign repositoryDesign) {
        this.contextDesign = contextDesign;
        this.loadService = loadService;
        this.repositoryDesign = repositoryDesign;

    }

    /**
     * Gets entry set.
     *
     * @return the entry set
     */
    public Set<Entry<String, Design>> getEntrySet() {
        return contextDesign.getContext().entrySet();
    }

    /**
     * Add new design context.
     *
     * @param designInfo the design info
     * @throws Exception the exception
     */
    public void addNewDesignContext(Design designInfo) throws Exception {
        designInfo.setLocalDateTime(LocalDateTime.now(this.zoneId));
        contextDesign.addDesign(designInfo);
    }

    /**
     * Sets condition design.
     *
     * @param conditionDto the condition dto
     * @return the condition design
     * @throws Exception the exception
     */
    public Design setConditionDesign(ConditionDto conditionDto) throws Exception {
        Design design = contextDesign.getDesign(conditionDto.getNameDesign());
        Condition condition = conditionDto.getCondition();
        design.setCondition(condition);
        contextDesign.update(design);
        return design;
    }

    /**
     * Sets condition panel.
     *
     * @param panelDto the panel dto
     * @return the condition panel
     * @throws Exception the exception
     */
    public Design setPanelDesign(@Valid PanelDto panelDto) throws Exception {
        Design design = contextDesign.getDesign(panelDto.getNameDesign());
        Panel panel = panelDto.getPanel();
        design.setPanel(panel);
        contextDesign.update(design);
        return design;
    }

    /**
     * Sets condition load.
     *
     * @param selectionLoadDto the selection load dto
     * @return the condition load
     * @throws Exception the exception
     */
    public Design setLoadDesign(@Valid SelectionLoadDto selectionLoadDto) throws Exception {
        Design design = contextDesign.getDesign(selectionLoadDto.getNameDesign());
        loadService.loadDtoList(selectionLoadDto.getLoadDtoList());
        List<Load> loadList = loadService.buildLoads();
        design.setLoadList(loadList);
        contextDesign.update(design);
        return design;
    }


    /**
     * Sets sizing.
     *
     * @param nameDesign the name design
     * @return the sizing
     * @throws Exception the exception
     */
    public Design setSizing(String nameDesign) throws Exception {
        Design design = contextDesign.getDesign(nameDesign);
        SizingDesign sizingDesign = new SizingDesign();
        CalculatorElectricalProcess.buildSizing(sizingDesign, design);
        design.setSizingDesign(sizingDesign);
        contextDesign.update(design);
        return design;


    }


    /**
     * Save design design.
     *
     * @param nameDesign the name design
     * @return the design
     * @throws Exception the exception
     */
    public Design saveDesign(String nameDesign) throws Exception {

        return repositoryDesign.save(contextDesign.getDesign(nameDesign));
    }

//Todo unit testing with mockito, junit mono
}
