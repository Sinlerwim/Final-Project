package com.FinalProject.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Laptop extends Computer{
    private double diagonal;
    private String screenResolution;
    private int refreshRate;
    private String webCamResolution;
    private int accumulatorCapacity;
    private float weight;
}
