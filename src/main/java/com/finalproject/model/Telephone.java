package com.finalproject.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Getter
@Setter
@Entity
public class Telephone {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private int price;
    private int numberOfSIM;
    private String simSlotType;
    private double diagonal;
    private String screenResolution;
    private int refreshRate;
    private int pixelDensity;
    private String screenType;

    @ManyToOne
    private Processor processor;

    private String internalMemory;
    private int ram;
    private String camera;
    private String operatingSystem;
    private String bluetooth;
    private String wifi;
    private String socket;
    private String material;
    private String color;
}
