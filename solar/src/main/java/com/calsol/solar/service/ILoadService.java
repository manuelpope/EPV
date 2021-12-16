package com.calsol.solar.service;

import com.calsol.solar.domain.dto.LoadDto;
import com.calsol.solar.domain.entity.Load;

import java.util.List;

/**
 * The interface Load.
 */
public interface ILoadService {

    /**
     * Power 12 volts dc double.
     *
     * @param loadDto the load dto
     * @return the double
     */
    Double power12VoltsDC(LoadDto loadDto);

    /**
     * Power 110 volts ac double.
     *
     * @param loadDto the load dto
     * @return the double
     */
    Double power110VoltsAC(LoadDto loadDto);

    /**
     * Energy day double.
     *
     * @param loadDto the load dto
     * @return the double
     */
    Double energyDay(LoadDto loadDto);

    /**
     * Energy night double.
     *
     * @param loadDto the load dto
     * @return the double
     */
    Double energyNight(LoadDto loadDto);

    /**
     * Total energy double.
     *
     * @param loadDto the load dto
     * @return the double
     */
    Double totalEnergy(LoadDto loadDto);

    /**
     * Sets type.
     *
     * @param loadDto the load dto
     * @return the type
     */
    String setType(LoadDto loadDto);

    /**
     * Load dto list.
     *
     * @param loadDtoList the load dto list
     */
    void loadDtoList(List<LoadDto> loadDtoList);

    /**
     * Build loads list.
     *
     * @return the list
     */
    List<Load> buildLoads();

}
