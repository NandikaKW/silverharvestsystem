package com.shpms.silverharvestsystem.service.impl;

import com.shpms.silverharvestsystem.dto.MoniteringLogDto;
import com.shpms.silverharvestsystem.entity.MoniteringLog;
import com.shpms.silverharvestsystem.repository.MoniteringLogRepo;
import com.shpms.silverharvestsystem.service.MonitoringLogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringLogService {
    private final MoniteringLogRepo logRepo;
    private final ModelMapper modelMapper;


    @Override
    public void saveLog(MoniteringLogDto logDto) {
        MoniteringLog log = modelMapper.map(logDto, MoniteringLog.class);
        logRepo.save(log);
    }

    @Override
    public List<MoniteringLogDto> getAllLogs() {
        return logRepo.findAll()
                .stream()
                .map(log -> modelMapper.map(log, MoniteringLogDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MoniteringLogDto getLogById(String logCode) {
        return logRepo.findById(logCode)
                .map(log -> modelMapper.map(log, MoniteringLogDto.class))
                .orElseThrow(() -> new RuntimeException("Log not found with ID: " + logCode));
    }

    @Override
    public void updateLog(String logCode, MoniteringLogDto logDto) {
        MoniteringLog existingLog = logRepo.findById(logCode)
                .orElseThrow(() -> new RuntimeException("Log not found with ID: " + logCode));

        existingLog.setLogDate(logDto.getLogDate());
        existingLog.setLogDetails(logDto.getLogDetails());
        existingLog.setObservedImage(logDto.getObservedImage());

        logRepo.save(existingLog);
    }

    @Override
    public void deleteLog(String logCode) {
        if (!logRepo.existsById(logCode)) {
            throw new RuntimeException("Log not found with ID: " + logCode);
        }
        logRepo.deleteById(logCode);
    }
}
