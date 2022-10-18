package com.finalproject.dto;

import com.finalproject.model.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ComputerUpdateDTO {
    private String id;
    private String manufacturer;
    private String model;
    private String ram;
    private String ramType;
    private String operatingSystem;

    private int price;
    private List<Image> images;
    List<MultipartFile> newImages;
    private String description;

    private String processorId;
    private String videoCardId;
    private String diskDriveId;

}
