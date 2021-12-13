package com.calsol.solar.controller;

import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.repository.dao.IRepositoryDesign;
import com.calsol.solar.service.ContextDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;


/**
 * The type Controller design.
 */
@RestController
@RequestMapping("/v1/calc")
public class ControllerDesign {

    private ZoneId zoneId;
    @Autowired
    private com.calsol.solar.repository.dao.IRepositoryDesign IRepositoryDesign;
    @Autowired
    private ContextDesign contextDesign;


    /**
     * Instantiates a new Controller design.
     *
     * @param IRepositoryDesign the repository design
     */
    public ControllerDesign(IRepositoryDesign IRepositoryDesign) {
        this.IRepositoryDesign = IRepositoryDesign;
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


}
