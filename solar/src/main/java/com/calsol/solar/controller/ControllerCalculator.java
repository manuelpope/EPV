package com.calsol.solar.controller;

import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.repository.dao.IRepositoryDesign;
import com.calsol.solar.service.ContextDesign;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;


/**
 * The type Controller calculator.
 */
@RestController
@RequestMapping("/v1/design")
public class ControllerCalculator {


    private ZoneId zoneId;
    @Autowired
    private IRepositoryDesign IRepositoryDesign;
    @Autowired
    private ContextDesign contextDesign;

    /**
     * Instantiates a new Controller calculator.
     *
     * @param IRepositoryDesign the repository design
     */
    public ControllerCalculator(IRepositoryDesign IRepositoryDesign) {
        this.IRepositoryDesign = IRepositoryDesign;
        this.zoneId = TimeZone.getTimeZone("UTC").toZoneId();
    }

    /**
     * Add design info design.
     *
     * @param designInfo the design info
     * @return the design
     */
    @PostMapping("/designinfo")
    @ResponseStatus(HttpStatus.CREATED)
    public Design addDesignInfo(@RequestBody @Valid Design designInfo) {

        designInfo.setLocalDateTime(LocalDateTime.now(zoneId));
        return IRepositoryDesign.save(designInfo);

    }

    @PostMapping("/designinit")
    @ResponseStatus(HttpStatus.OK)
    public Design putInMap(@RequestBody @Valid Design designInfo) {

        designInfo.setLocalDateTime(LocalDateTime.now(zoneId));
        contextDesign.addDesign(designInfo);
        return designInfo;

    }

    /**
     * Gets design info.
     *
     * @param name the name
     * @return the design info
     */
    @GetMapping("/designinfobyname/{name}")
    public ResponseEntity getDesignInfo(@PathVariable("name") String name) {

        Gson gson = new Gson();
        return ResponseEntity.ok(Optional.ofNullable(IRepositoryDesign.findByName(name)).map(gson::toJson).orElse("Not Found"));

    }

    /**
     * Gets design info.
     *
     * @param page the page
     * @return the design info
     */
    @GetMapping("/designinfo/{page}")
    public List<Design> getDesignInfo(@PathVariable("page") int page) {
        Pageable pages = PageRequest.of(page,10);

        return IRepositoryDesign.findAll(pages).toList();

    }

}
