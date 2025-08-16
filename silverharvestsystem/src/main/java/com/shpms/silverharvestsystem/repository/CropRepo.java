package com.shpms.silverharvestsystem.repository;

import com.shpms.silverharvestsystem.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRepo  extends JpaRepository<Crop, String> {
}
