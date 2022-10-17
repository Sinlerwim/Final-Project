package com.finalproject.repository;

import com.finalproject.model.DiskDrive;
import com.finalproject.model.DiskDriveType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Repository
public interface DiskDriveRepository extends CrudRepository<DiskDrive, String> {
    boolean existsByNameAndTypeAndCapacityAllIgnoreCase(@NotEmpty String name, @NotNull DiskDriveType type, @NotEmpty String capacity);

    DiskDrive findFirstByNameAndTypeAndCapacityAllIgnoreCase(@NotEmpty String name, @NotNull DiskDriveType type, @NotEmpty String capacity);



}
