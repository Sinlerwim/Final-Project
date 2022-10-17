package com.finalproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class ComputerCreationDTO {
    @NotEmpty(message = "Manufacturer field can't be empty")
    private String manufacturer;

    @NotEmpty(message = "Model field can't be empty")
    private String model;

    private String ram;
    private String ramType;
    private String operatingSystem;

    @Min(0)
    private int price;
    private List<MultipartFile> image;
    private String description;

    private String processorId;
    private String videoCardId;
    private String diskDriveId;
}
