package com.finalproject.dto;

import com.finalproject.model.DiskDriveType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DiskDriveCreationDTO {
    @NotEmpty(message = "Name field can't be empty")
    private String name;

    @NotNull(message = "Disk drive type cannot be null")
    private DiskDriveType type;

    private String capacity;
}
