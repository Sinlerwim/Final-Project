package com.finalproject.dto;

import com.finalproject.model.DiskDriveType;
import com.finalproject.model.ProcessorManufacturer;
import com.finalproject.model.VideoCardType;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ComputerDTO {

    private String id;
    private String manufacturer;
    private String model;
    private String ram;
    private String ramType;
    private String operatingSystem;

    private int price;
    private List<byte[]> images;
    private String description;

    private ProcessorManufacturer processorManufacturer;
    private String processorModel;
    private String processorFrequency;

    private String videoCardManufacturer;
    private String videoCardModel;
    private VideoCardType videoCardType;
    private String vram;

    private String diskDriveName;
    private DiskDriveType diskDriveType;
    private String diskDriveCapacity;
}
