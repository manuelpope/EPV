package com.calsol.solar.service;

import com.calsol.solar.domain.dto.LoadDto;
import com.calsol.solar.domain.entity.Load;

import java.util.List;

/**
 * The interface Load.
 */
public interface ILoadService {

    public Double power12VoltsDC(LoadDto loadDto);

    public Double power110VoltsAC(LoadDto loadDto);

    public Double energyDay(LoadDto loadDto);

    public Double energyNight(LoadDto loadDto);

    public Double totalEnergy(LoadDto loadDto);

    public String setType(LoadDto loadDto);

    public void loadDtoList(List<LoadDto> loadDtoList);

    public List<Load> buildLoads();

}
