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

    /**
     * The constant LOAD_DTO_PREDICATE_12.
     */
    public static final Predicate<LoadDto> LOAD_DTO_PREDICATE_12 = s -> Math.abs(s.getVoltage() - 12.0) < 0.0001d;
    /**
     * The constant LOAD_DTO_PREDICATE_110.
     */
    public static final Predicate<LoadDto> LOAD_DTO_PREDICATE_110 = s -> Math.abs(s.getVoltage() - 110.0) < 0.0001d;

    private List<LoadDto> loadDtoList = new ArrayList<>();

    /**
     * Validation safe type.
     *
     * @param l the l
     * @throws Exception the exception
     */
    public static void validationSafeType(LoadDto l) throws Exception {

        Optional.ofNullable(l).filter(LOAD_DTO_PREDICATE_12).filter(LOAD_DTO_PREDICATE_110).orElseThrow(() -> new Exception("Not a valid voltage(110 or 12) -- " + l.getVoltage()));

    }

    @Override
    public Double power12VoltsDC(LoadDto loadDto) {
        return Optional.ofNullable(loadDto).filter(LOAD_DTO_PREDICATE_12)
                .map(r -> r.getPower() * r.getQuantity()).orElse(0.0);
    }

    @Override
    public Double power110VoltsAC(LoadDto loadDto) {
        return Optional.ofNullable(loadDto).filter(LOAD_DTO_PREDICATE_110)
                .map(r -> r.getPower() * r.getQuantity()).orElse(0.0);
    }

    @Override
    public Double energyDay(LoadDto loadDto) {
        return loadDto.getWorkingDayHours() * loadDto.getQuantity() * loadDto.getPower();
    }

    @Override
    public Double energyNight(LoadDto loadDto) {
        return loadDto.getWorkingNightHours() * loadDto.getQuantity() * loadDto.getPower();

    }

    @Override
    public Double totalEnergy(LoadDto loadDto) {
        return energyDay(loadDto) + energyNight(loadDto);
    }

    @Override
    public String setType(LoadDto loadDto) {
        return Optional.ofNullable(loadDto).filter(LOAD_DTO_PREDICATE_12)
                .map(s -> "DC").orElse("AC");
    }

    @Override
    public void loadDtoList(List<LoadDto> loadDtoList) throws Exception {
        if ((loadDtoList.stream().filter(LOAD_DTO_PREDICATE_110.negate()).anyMatch(LOAD_DTO_PREDICATE_12.negate()))) {
            throw new Exception("Not a valid voltage(110 or 12) --");
        }
        this.loadDtoList = loadDtoList;
    }

    /**
     * Calculate load load.
     *
     * @param loadDto the load dto
     * @return the load
     */
    private Load calculateLoad(LoadDto loadDto) {
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
