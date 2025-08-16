package com.shpms.silverharvestsystem.controller;

import com.shpms.silverharvestsystem.dto.MoniteringLogDto;
import com.shpms.silverharvestsystem.service.MonitoringLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
@Slf4j
public class MonitorLogController {

    private final MonitoringLogService monitoringLogService;


    @PostMapping("/save")
    public ResponseEntity<String> saveLog(@RequestBody MoniteringLogDto logDto) {
        monitoringLogService.saveLog(logDto);
        return ResponseEntity.ok("Monitoring Log saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MoniteringLogDto>> getAllLogs() {
        return ResponseEntity.ok(monitoringLogService.getAllLogs());
    }

    @GetMapping("/{logCode}")
    public ResponseEntity<MoniteringLogDto> getLogById(@PathVariable String logCode) {
        return ResponseEntity.ok(monitoringLogService.getLogById(logCode));
    }

    @PutMapping("/update/{logCode}")
    public ResponseEntity<String> updateLog(@PathVariable String logCode, @RequestBody MoniteringLogDto logDto) {
        monitoringLogService.updateLog(logCode, logDto);
        return ResponseEntity.ok("Monitoring Log updated successfully");
    }

    @DeleteMapping("/delete/{logCode}")
    public ResponseEntity<String> deleteLog(@PathVariable String logCode) {
        monitoringLogService.deleteLog(logCode);
        return ResponseEntity.ok("Monitoring Log deleted successfully");
    }



}
