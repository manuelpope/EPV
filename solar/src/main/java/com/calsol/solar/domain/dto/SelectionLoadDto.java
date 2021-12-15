package com.calsol.solar.domain.dto;

import com.calsol.solar.domain.IDevice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectionLoadDto implements IDevice {

    private String nameDesign;
    private List<LoadDto> loadDtoList;

}
