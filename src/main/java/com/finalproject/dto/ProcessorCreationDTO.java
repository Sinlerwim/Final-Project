package com.finalproject.dto;

import com.finalproject.model.ProcessorManufacturer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProcessorCreationDTO {

    @NotNull(message = "Manufacturer field can't be null")
    private ProcessorManufacturer manufacturer;

    @NotEmpty(message = "Model field can't be empty")
    private String model;
    private String frequency;
}
