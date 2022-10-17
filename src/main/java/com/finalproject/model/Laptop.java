package com.finalproject.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Laptop{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private double diagonal;
    private String screenResolution;
    private int refreshRate;
    private String webCamResolution;
    private int accumulatorCapacity;
    private float weight;
}
