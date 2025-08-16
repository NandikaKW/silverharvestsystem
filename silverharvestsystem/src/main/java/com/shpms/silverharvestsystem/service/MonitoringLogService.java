package com.shpms.silverharvestsystem.service;

import com.shpms.silverharvestsystem.dto.MoniteringLogDto;

import java.util.List;

public interface MonitoringLogService {
    void saveLog(MoniteringLogDto logDto);
    List<MoniteringLogDto> getAllLogs();
    MoniteringLogDto getLogById(String logCode);
    void updateLog(String logCode, MoniteringLogDto logDto);
    void deleteLog(String logCode);
}
