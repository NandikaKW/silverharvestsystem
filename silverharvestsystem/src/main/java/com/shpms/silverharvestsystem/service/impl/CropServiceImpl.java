package com.shpms.silverharvestsystem.service.impl;

import com.shpms.silverharvestsystem.dto.CropDTO;
import com.shpms.silverharvestsystem.entity.Crop;
import com.shpms.silverharvestsystem.entity.Field;
import com.shpms.silverharvestsystem.entity.MoniteringLog;
import com.shpms.silverharvestsystem.exception.DataPersistException;
import com.shpms.silverharvestsystem.repository.CropRepo;
import com.shpms.silverharvestsystem.repository.FieldRepo;
import com.shpms.silverharvestsystem.repository.MoniteringLogRepo;
import com.shpms.silverharvestsystem.service.CropService;
import com.shpms.silverharvestsystem.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {
    private final CropRepo cropRepo;
    private final FieldRepo fieldRepo;
    private final MoniteringLogRepo logRepo;
    private final ModelMapper modelMapper;
    private final Mapping noteMapping; // Mapper to convert between DTO and Entity

    @Override
    public List<CropDTO> getAllCrops() {
        return cropRepo.findAll()
                .stream()
                .map(crop -> {
                    CropDTO dto = modelMapper.map(crop, CropDTO.class);
                    dto.setFieldCode(crop.getField().getFieldCode());
                    dto.setLogCode(crop.getLog().getLogCode());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CropDTO getCropById(String cropCode) {
        Crop crop = cropRepo.findById(cropCode)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        CropDTO dto = modelMapper.map(crop, CropDTO.class);
        dto.setFieldCode(crop.getField().getFieldCode());
        dto.setLogCode(crop.getLog().getLogCode());
        return dto;
    }

    @Override
    public CropDTO saveCrop(CropDTO cropDto) {

        Field field = fieldRepo.findById(cropDto.getFieldCode())
                .orElseThrow(() -> new DataPersistException("Field not found"));

        MoniteringLog log = logRepo.findById(cropDto.getLogCode())
                .orElseThrow(() -> new DataPersistException("Log not found"));


        Crop cropEntity = noteMapping.toCropEntity(cropDto);
        cropEntity.setField(field);
        cropEntity.setLog(log);


        Crop savedCrop = cropRepo.save(cropEntity);
        if (savedCrop == null) {
            throw new DataPersistException("CROP not saved");
        }

        return noteMapping.toCropDTO(savedCrop);
    }


    @Override
    public void updateCrop(String CropId, CropDTO cropDto) {
        Optional<Crop> findNote = cropRepo.findById(CropId);
        if (!findNote.isPresent()) {
            throw new DataPersistException("Note not found");
        }else {
            findNote.get().setCategory(cropDto.getCategoryId());
            findNote.get().setCropImage(cropDto.getCropImage());
            findNote.get().setCropSeason(cropDto.getSeasonId());
            findNote.get().setCommonName(cropDto.getCommonName());
            findNote.get().setScientificName(cropDto.getScientificName());


        }
    }

    @Override
    public void deleteCrop(String cropCode) {
        if (!cropRepo.existsById(cropCode)) {
            throw new RuntimeException("Crop not found");
        }
        cropRepo.deleteById(cropCode);
    }
}
