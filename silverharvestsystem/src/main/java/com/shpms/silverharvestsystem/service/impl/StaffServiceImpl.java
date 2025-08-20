package com.shpms.silverharvestsystem.service.impl;

import com.shpms.silverharvestsystem.dto.FieldDto;
import com.shpms.silverharvestsystem.dto.StaffDto;
import com.shpms.silverharvestsystem.entity.Equipment;
import com.shpms.silverharvestsystem.entity.Field;
import com.shpms.silverharvestsystem.entity.Staff;
import com.shpms.silverharvestsystem.entity.Vehicle;
import com.shpms.silverharvestsystem.exception.DataPersistException;
import com.shpms.silverharvestsystem.repository.FieldRepo;
import com.shpms.silverharvestsystem.repository.StaffRepo;
import com.shpms.silverharvestsystem.service.StaffService;
import com.shpms.silverharvestsystem.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepo staffRepo;
    private final ModelMapper modelMapper;
    private Mapping mapping;
    private FieldRepo fieldRepo;

    @Override

    public void SaveStaff(StaffDto staffDto) {
        Staff staff = mapping.toStaffEntity(staffDto);


        List<Field> fields = new ArrayList<>();
        if (staffDto.getFields() != null) {
            for (FieldDto fieldDto : staffDto.getFields()) {
                Field field = fieldRepo.findById(fieldDto.getFieldCode())
                        .orElseThrow(() -> new DataPersistException("Field not found with ID: " + fieldDto.getFieldCode()));
                fields.add(field);

                // Add staff to the field's staff list to maintain bidirectional relationship
                if (!field.getStaffs().contains(staff)) {
                    field.getStaffs().add(staff);
                }
            }
        }

        staff.setFields(fields);


        // Save staff entity
        Staff savedStaff = staffRepo.save(staff);
        if (savedStaff == null) {
            throw new DataPersistException("Staff not saved");
        }
    }

    @Override
    public StaffDto updateStaff(String staffId, StaffDto staffDto) {
        Staff existingStaff = staffRepo.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));

        existingStaff.setFirstName(staffDto.getFirstName());
        existingStaff.setLastName(staffDto.getLastName());
        existingStaff.setDesignation(staffDto.getDesignation());
        existingStaff.setGender(staffDto.getGender());
        existingStaff.setJoinedDate(staffDto.getJoinedDate());
        existingStaff.setDOB(staffDto.getDOB());
        existingStaff.setAddress(staffDto.getAddress());
        existingStaff.setContact_No(staffDto.getContact_No());
        existingStaff.setEmail(staffDto.getEmail());
        existingStaff.setRole(staffDto.getRole());
        existingStaff.setVehicles(staffDto.getVehicleDtos() != null ?
                staffDto.getVehicleDtos().stream()
                        .map(v -> modelMapper.map(v, Vehicle.class))
                        .toList() : existingStaff.getVehicles());
        existingStaff.setEquipment(staffDto.getEquipmentDtos() != null ?
                staffDto.getEquipmentDtos().stream()
                        .map(e -> modelMapper.map(e, Equipment.class))
                        .toList() : existingStaff.getEquipment());
        existingStaff.setFields(staffDto.getFields() != null ?
                staffDto.getFields().stream()
                        .map(f -> modelMapper.map(f, Field.class))
                        .toList() : existingStaff.getFields());

        Staff updated = staffRepo.save(existingStaff);
        return modelMapper.map(updated, StaffDto.class);
    }


    @Override
    public void deleteStaff(String staffId) {
        if (!staffRepo.existsById(staffId)) {
            throw new RuntimeException("Staff not found with id: " + staffId);
        }
        staffRepo.deleteById(staffId);
    }

    @Override
    public StaffDto getStaffById(String staffId) {
        Staff staff = staffRepo.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
        return modelMapper.map(staff, StaffDto.class);
    }

    @Override
    public List<StaffDto> getAllStaff() {
        return staffRepo.findAll()
                .stream()
                .map(staff -> modelMapper.map(staff, StaffDto.class))
                .collect(Collectors.toList());
    }
}
