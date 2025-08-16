package com.shpms.silverharvestsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class FieldDto implements SuperDTO{
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private Double Extent_size;
    private String fieldImageOne;
    private String fieldImageTwo;
    private java.util.List<CropDTO> crops;
    private java.util.List<EquipmentDto> equipmentDtos;
    @ToString.Exclude
    @JsonIgnore
    private List<StaffDto> staffs;
    private String logCode;
}
