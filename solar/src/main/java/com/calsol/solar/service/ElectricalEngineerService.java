package com.calsol.solar.service;

import com.calsol.solar.domain.dto.LoadDto;
import com.calsol.solar.domain.entity.Load;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The type Electrical engineer service.
 */
@Service
@Data
public class ElectricalEngineerService implements ILoadService {

    public static final Predicate<LoadDto> LOAD_DTO_PREDICATE_12 = s -> Math.abs(s.getVoltage() - 12.0) < 0.000001d;
    public static final Predicate<LoadDto> LOAD_DTO_PREDICATE_110 = s -> Math.abs(s.getVoltage() - 110.0) < 0.000001d;
    public static final Predicate<LoadDto> LOAD_DTO_PREDICATE_TYPE = s -> Math.abs(s.getVoltage() - 12.0) < 0.000001d;
    private List<LoadDto> loadDtoList = new ArrayList<>();


    @Override
    public Double power12VoltsDC(LoadDto loadDto) {
        return Optional.ofNullable(loadDto).filter(LOAD_DTO_PREDICATE_12)
                .map(r -> r.getPowerDC() * r.getQuantity()).orElse(0.0);
    }

    @Override
    public Double power110VoltsAC(LoadDto loadDto) {
        return Optional.ofNullable(loadDto).filter(LOAD_DTO_PREDICATE_110)
                .map(r -> r.getPowerDC() * r.getQuantity()).orElse(0.0);
    }

    @Override
    public Double energyDay(LoadDto loadDto) {
        return loadDto.getWorkingDayHours() * loadDto.getQuantity() * loadDto.getPowerDC();
    }

    @Override
    public Double energyNight(LoadDto loadDto) {
        return loadDto.getWorkingNightHours() * loadDto.getQuantity() * loadDto.getPowerDC();

    }

    @Override
    public Double totalEnergy(LoadDto loadDto) {
        return energyDay(loadDto) + energyNight(loadDto);
    }

    @Override
    public String setType(LoadDto loadDto) {
        return Optional.ofNullable(loadDto).filter(LOAD_DTO_PREDICATE_TYPE)
                .map(s -> "DC").orElse("AC");
    }

    @Override
    public void loadDtoList(List<LoadDto> loadDtoList) {
        this.loadDtoList = loadDtoList;
    }

    public Load calculateLoad(LoadDto loadDto) {
        return Load.builder().power110VoltsAC(this.power110VoltsAC(loadDto))
                .power12VoltsDC(this.power12VoltsDC(loadDto))
                .energyDay(energyDay(loadDto))
                .energyNight(energyNight(loadDto))
                .totalEnergy(totalEnergy(loadDto))
                .type(setType(loadDto)).build();

    }

    public List<Load> buildLoads() {

        return this.loadDtoList.stream().map(this::calculateLoad).collect(Collectors.toList());

    }
}
