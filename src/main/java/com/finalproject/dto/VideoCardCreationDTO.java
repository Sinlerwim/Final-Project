package com.finalproject.dto;

import com.finalproject.model.VideoCardType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VideoCardCreationDTO {

    @NotEmpty(message = "Manufacturer field can't be empty")
    private String manufacturer;

    @NotEmpty(message = "Model field can't be empty")
    private String model;

    @NotNull(message = "Video card type cannot be null")
    private VideoCardType type;
    private String vram;
}
