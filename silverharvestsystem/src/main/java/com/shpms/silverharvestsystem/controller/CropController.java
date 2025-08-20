package com.shpms.silverharvestsystem.controller;

import com.shpms.silverharvestsystem.dto.CropDTO;
import com.shpms.silverharvestsystem.exception.DataPersistException;
import com.shpms.silverharvestsystem.service.CropService;
import com.shpms.silverharvestsystem.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(@RequestParam("cropCode") String cropCode,
                                         @RequestParam("commonName") String commonName,
                                         @RequestParam("scientificName") String scientificName,
                                         @RequestParam("cropImage") MultipartFile cropImage,
                                         @RequestParam("category") String category,
                                         @RequestParam("cropSeason") String cropSeason,
                                         @RequestParam("fieldCode") String fieldCode ,
                                         @RequestParam("logCode") String logCode) {
        try {
            String base64ProPic = "";

            byte [] bytesProPic = cropImage.getBytes();
            base64ProPic = AppUtil.ImageToBase64(bytesProPic);

            CropDTO cropDto = new CropDTO();
            cropDto.setCropCode(cropCode);
            cropDto.setCommonName(commonName);
            cropDto.setScientificName(scientificName);
            cropDto.setCropImage(base64ProPic);
            cropDto.setCategoryId(category);
            cropDto.setSeasonId(cropSeason);
            cropDto.setFieldCode(fieldCode);
            cropDto.setLogCode(logCode);



            cropService.saveCrop(cropDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{cropId}")
    public ResponseEntity<Void> updateNote(@PathVariable ("cropId") String cropId,
                                           @RequestParam("commonName") String commonName,
                                           @RequestParam("scientificName") String scientificName,
                                           @RequestParam("cropImage") MultipartFile cropImage,
                                           @RequestParam("category") String category,
                                           @RequestParam("cropSeason") String cropSeason){
        //validations
        try {
            String base64ProPic = "";

            byte[] bytesProPic = cropImage.getBytes();
            base64ProPic = AppUtil.ImageToBase64(bytesProPic);


            CropDTO cropDto = new CropDTO();
            cropDto.setCropCode(cropId);
            cropDto.setCommonName(commonName);
            cropDto.setScientificName(scientificName);
            cropDto.setCropImage(base64ProPic);
            cropDto.setCategoryId(category);
            cropDto.setSeasonId(cropSeason);

            cropService.updateCrop(cropId,cropDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{cropCode}")
    public ResponseEntity<String> deleteCrop(@PathVariable String cropCode) {
        cropService.deleteCrop(cropCode);
        return ResponseEntity.ok("Crop deleted successfully");
    }
}
