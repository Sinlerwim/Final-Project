package com.finalproject.service;

import com.finalproject.repository.LaptopRepository;
import org.springframework.stereotype.Service;

@Service
public class LaptopService {

    private final LaptopRepository laptopRepository;


    public LaptopService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }
}
