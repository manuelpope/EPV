package com.calsol.solar.controller;

import com.calsol.solar.domain.entity.Design;
import com.calsol.solar.repository.dao.IRepositoryDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    /**
     * Gets design info.
     *
     * @param name the name
     * @return the design info
     */
    @GetMapping("/designinfobyname/{name}")
    public String getDesignInfo(@PathVariable("name") String name) {


        return Optional.ofNullable(IRepositoryDesign.findByName(name)).map(Design::toString).orElse("Not Found");

    }

    /**
     * Gets design info.
     *
     * @param page the page
     * @return the design info
     */
    @GetMapping("/designinfo/{page}")
    public List<Design> getDesignInfo(@PathVariable("page") int page) {
        Pageable pages = Pageable.ofSize(10);
        pages.withPage(page);

        return IRepositoryDesign.findAll(pages).toList();

    }

}
