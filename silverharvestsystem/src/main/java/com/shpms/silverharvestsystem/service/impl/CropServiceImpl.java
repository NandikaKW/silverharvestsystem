package com.shpms.silverharvestsystem.service.impl;

import com.shpms.silverharvestsystem.dto.CropDTO;
import com.shpms.silverharvestsystem.entity.Crop;
import com.shpms.silverharvestsystem.entity.Field;
import com.shpms.silverharvestsystem.entity.MoniteringLog;
import com.shpms.silverharvestsystem.repository.CropRepo;
import com.shpms.silverharvestsystem.repository.FieldRepo;
import com.shpms.silverharvestsystem.repository.MoniteringLogRepo;
import com.shpms.silverharvestsystem.service.CropService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {
    private final CropRepo cropRepo;
    private final ModelMapper modelMapper;



    @Override
    public List<CropDTO> getAllCrops() {
        return cropRepo.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveCrop(CropDTO cropDTO) {
        if (cropRepo.existsById(cropDTO.getCropCode())) {
            throw new RuntimeException("Crop already exists");
        }
        cropRepo.save(modelMapper.map(cropDTO, Crop.class));

    }

    @Override
    public void updateCrop(String cropCode, CropDTO cropDTO) {
        Crop existingCrop = cropRepo.findById(cropCode)
                .orElseThrow(() -> new RuntimeException("Crop not found"));
        existingCrop.setCommonName(cropDTO.getCommonName());
        existingCrop.setScientificName(cropDTO.getScientificName());
        existingCrop.setCropImage(cropDTO.getImage());
        existingCrop.setCategory(cropDTO.getCategoryId());
        existingCrop.setCropSeason(cropDTO.getSeasonId());

        cropRepo.save(existingCrop);
    }


    @Override
    public void deleteCrop(String cropCode) {
      cropRepo.deleteById(cropCode);
    }

    @Override
    public CropDTO getCropById(String cropCode) {
       if (!cropRepo.existsById(cropCode)) {
           throw new RuntimeException("Crop not found");
       }
        return modelMapper.map(cropRepo.findById(cropCode).get(), CropDTO.class);
    }
}
