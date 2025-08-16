package com.shpms.silverharvestsystem.repository;

import com.shpms.silverharvestsystem.entity.MoniteringLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoniteringLogRepo extends JpaRepository<MoniteringLog, String> {
}
