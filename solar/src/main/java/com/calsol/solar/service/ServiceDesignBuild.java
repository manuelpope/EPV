package com.calsol.solar.service;

import com.calsol.solar.domain.dto.ConditionDto;
import com.calsol.solar.domain.entity.Condition;
import com.calsol.solar.domain.entity.Design;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ServiceDesignBuild {
    @Autowired
    private ContextDesign contextDesign;


    public Design setConditionDesign(ConditionDto conditionDto) throws Exception {
        Design design = contextDesign.getDesign(conditionDto.getNameDesign());
        Condition condition = conditionDto.getCondition();
        design.setCondition(condition);
        contextDesign.update(design);
        return design;
    }



//Todo unit testing with mockito, junit mono
}
