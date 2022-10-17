package com.finalproject.mapper;

import com.finalproject.dto.DiskDriveCreationDTO;
import com.finalproject.model.DiskDrive;

public class DiskDriveMapper {

    public static DiskDrive fromDiskDriveCreationDTO(DiskDriveCreationDTO diskDriveCreationDTO) {
        final DiskDrive diskDrive = new DiskDrive();
        diskDrive.setName(diskDriveCreationDTO.getName());
        diskDrive.setCapacity(diskDriveCreationDTO.getCapacity());
        diskDrive.setType(diskDriveCreationDTO.getType());
        return diskDrive;
    }
}
