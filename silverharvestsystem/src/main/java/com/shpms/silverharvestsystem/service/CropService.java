package com.shpms.silverharvestsystem.service;

import com.shpms.silverharvestsystem.dto.CropDTO;

import java.util.List;

public interface CropService {
     List<CropDTO>  getAllCrops();
    void saveCrop(CropDTO cropDTO);
    void updateCrop(String cropCode, CropDTO cropDTO);
    void deleteCrop(String cropCode);
    CropDTO getCropById(String cropCode);
}
