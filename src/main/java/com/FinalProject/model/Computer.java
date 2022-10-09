package com.FinalProject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Computer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String manufacturer;
    private String model;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;
    private String ram;
    private String ramType;

    @ManyToOne(cascade = CascadeType.ALL)
    private Processor processor;

    @ManyToOne(cascade = CascadeType.ALL)
    private VideoCard videoCard;

    @ManyToOne(cascade = CascadeType.ALL)
    private DiskDrive diskDrive;

    private String operatingSystem;
    private String color;
    private String material;
    private String size;
    @Min(0)
    private int price;
    private String description;

}
