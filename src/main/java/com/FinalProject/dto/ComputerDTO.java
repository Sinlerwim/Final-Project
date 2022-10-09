package com.FinalProject.dto;

import lombok.Getter;
import lombok.Setter;

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
    private byte[] image;
}
