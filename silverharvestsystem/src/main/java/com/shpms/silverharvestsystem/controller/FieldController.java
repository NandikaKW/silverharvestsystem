package com.shpms.silverharvestsystem.controller;

import com.shpms.silverharvestsystem.dto.FieldDto;
import com.shpms.silverharvestsystem.exception.DataPersistException;
import com.shpms.silverharvestsystem.service.FieldService;
import com.shpms.silverharvestsystem.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/field")
@RequiredArgsConstructor
@Slf4j
public class FieldController {

    private final FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveField(
            @RequestParam("fieldCode") String fieldCode,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocation") String fieldLocation,
            @RequestParam("extent_size") Double extentSize,
            @RequestParam("fieldImageOne") MultipartFile fieldImageOne,
            @RequestParam("fieldImageTwo") MultipartFile fieldImageTwo,
            @RequestParam("logCode") String logCode) {
        try {
            // Convert image files to Base64 strings
            String base64ImageOne = AppUtil.ImageToBase64(fieldImageOne.getBytes());
            String base64ImageTwo = AppUtil.ImageToBase64(fieldImageTwo.getBytes());

            // Create FieldDto
            FieldDto fieldDto = new FieldDto();
            fieldDto.setFieldCode(fieldCode);
            fieldDto.setFieldName(fieldName);
            fieldDto.setFieldLocation(fieldLocation);   // ✅ now a String
            fieldDto.setExtent_size(extentSize);
            fieldDto.setFieldImageOne(base64ImageOne);
            fieldDto.setFieldImageTwo(base64ImageTwo);
            fieldDto.setLogCode(logCode);

            // Save the field
            fieldService.SaveField(fieldDto);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{fieldCode}")
    public ResponseEntity<FieldDto> getField(@PathVariable String fieldCode) {
        FieldDto fieldDto = fieldService.getFieldById(fieldCode);
        return ResponseEntity.ok(fieldDto);
    }

    @GetMapping
    public ResponseEntity<List<FieldDto>> getAllFields() {
        List<FieldDto> fields = fieldService.getAllFields();
        return ResponseEntity.ok(fields);
    }

    @PutMapping(value = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("fieldCode") String fieldCode,
                                            @RequestParam("fieldName") String fieldName,
                                            @RequestParam("fieldLocation") String fieldLocation,
                                            @RequestParam("extent_size") Double extentSize,
                                            @RequestParam(value = "fieldImageOne", required = false) MultipartFile fieldImageOne,
                                            @RequestParam(value = "fieldImageTwo", required = false) MultipartFile fieldImageTwo,
                                            @RequestParam("logCode") String logCode) {
        try {
            String base64ImageOne = null;
            String base64ImageTwo = null;

            if (fieldImageOne != null && !fieldImageOne.isEmpty()) {
                base64ImageOne = AppUtil.ImageToBase64(fieldImageOne.getBytes());
            }
            if (fieldImageTwo != null && !fieldImageTwo.isEmpty()) {
                base64ImageTwo = AppUtil.ImageToBase64(fieldImageTwo.getBytes());
            }

            FieldDto updatedFieldDTO = new FieldDto();
            updatedFieldDTO.setFieldCode(fieldCode);
            updatedFieldDTO.setFieldName(fieldName);
            updatedFieldDTO.setFieldLocation(fieldLocation);
            updatedFieldDTO.setExtent_size(extentSize);
            updatedFieldDTO.setFieldImageOne(base64ImageOne);
            updatedFieldDTO.setFieldImageTwo(base64ImageTwo);
            updatedFieldDTO.setLogCode(logCode);

            fieldService.updateField(fieldCode, updatedFieldDTO);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable String fieldCode) {
        fieldService.deleteField(fieldCode);
        return ResponseEntity.noContent().build();
    }
}
