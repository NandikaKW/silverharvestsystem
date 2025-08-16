package com.shpms.silverharvestsystem.controller;

import com.shpms.silverharvestsystem.dto.CropDTO;
import com.shpms.silverharvestsystem.service.CropService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/crop")
@RequiredArgsConstructor
@Slf4j
public class CropController {
    private final CropService cropService;

    @GetMapping("/all")
    public ResponseEntity<List<CropDTO>> getAllCrops() {
        return ResponseEntity.ok(cropService.getAllCrops());
    }

    @GetMapping("/{cropCode}")
    public ResponseEntity<CropDTO> getCropById(@PathVariable String cropCode) {
        return ResponseEntity.ok(cropService.getCropById(cropCode));
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCrop(@RequestBody CropDTO cropDTO) {
        cropService.saveCrop(cropDTO);
        return ResponseEntity.ok("Crop saved successfully");
    }

    @PutMapping("/update/{cropCode}")
    public ResponseEntity<String> updateCrop(@PathVariable String cropCode, @RequestBody CropDTO cropDTO) {
        cropService.updateCrop(cropCode, cropDTO);
        return ResponseEntity.ok("Crop updated successfully");
    }


    @DeleteMapping("/{cropCode}")
    public ResponseEntity<String> deleteCrop(@PathVariable String cropCode) {
        cropService.deleteCrop(cropCode);
        return ResponseEntity.ok("Crop deleted successfully");
    }
}
