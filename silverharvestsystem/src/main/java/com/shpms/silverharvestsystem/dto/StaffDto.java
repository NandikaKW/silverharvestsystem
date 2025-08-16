package com.shpms.silverharvestsystem.dto;

import com.shpms.silverharvestsystem.Enum.Gender;
import com.shpms.silverharvestsystem.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StaffDto implements SuperDTO{
    private String StaffId;
    private String FirstName;
    private String LastName;
    private String Designation;
    private Gender Gender;
    private Date JoinedDate;
    private Date DOB;
    private String address;
    private String Contact_No;
    private String Email;
    private Role role;

    private List<VehicleDto> vehicleDtos;
    private List<EquipmentDto> equipmentDtos;

    @ToString.Exclude
    private List<FieldDto> fields;

    private String logCode;
}
