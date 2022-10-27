package com.finalproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Computer implements Comparable<Computer> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotEmpty
    private String manufacturer;

    @NotEmpty
    private String model;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "computer_id")
    private List<Image> images;
    private String ram;
    private String ramType;
    private String operatingSystem;
    @Min(0)
    private int price;
    private String description;

    @ManyToOne
    @JoinColumn(name="processor_id")
    private Processor processor;

    @ManyToOne
    @JoinColumn(name="videocard_id")
    private VideoCard videoCard;

    @ManyToOne
    @JoinColumn(name="diskdrive_id")
    private DiskDrive diskDrive;

    @Override
    public int compareTo(Computer computer) {
        return Integer.compare(this.price, computer.price);
    }
}
