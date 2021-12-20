package com.calsol.solar.service;

import com.calsol.solar.domain.dto.ConditionDto;
import com.calsol.solar.domain.dto.PanelDto;
import com.calsol.solar.domain.dto.SelectionLoadDto;
import com.calsol.solar.domain.entity.*;
import com.calsol.solar.util.CalculatorElectricalProcess;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@NoArgsConstructor
public class ServiceDesignBuild {
    @Autowired
    private ContextDesign contextDesign;
    @Autowired
    private ILoadService loadService;


    public Design setConditionDesign(ConditionDto conditionDto) throws Exception {
        Design design = contextDesign.getDesign(conditionDto.getNameDesign());
        Condition condition = conditionDto.getCondition();
        design.setCondition(condition);
        contextDesign.update(design);
        return design;
    }

    public Design setConditionPanel(@Valid PanelDto panelDto) throws Exception{
        Design design = contextDesign.getDesign(panelDto.getNameDesign());
        Panel panel = panelDto.getPanel();
        design.setPanel(panel);
        contextDesign.update(design);
        return design;
    }

    public Design setConditionLoad(@Valid SelectionLoadDto selectionLoadDto) throws Exception{
        Design design = contextDesign.getDesign(selectionLoadDto.getNameDesign());
        loadService.loadDtoList(selectionLoadDto.getLoadDtoList());
        List<Load> loadList = loadService.buildLoads();
        design.setLoadList(loadList);
        contextDesign.update(design);
        return design;
    }

    public Design setConditionName(String nameDesign) throws Exception{
        Design design = contextDesign.getDesign(nameDesign);
        SizingDesign sizingDesign = new SizingDesign();
        CalculatorElectricalProcess.buildSizing(sizingDesign, design);
        design.setSizingDesign(sizingDesign);
        return design;
    }

//Todo unit testing with mockito, junit mono
}
