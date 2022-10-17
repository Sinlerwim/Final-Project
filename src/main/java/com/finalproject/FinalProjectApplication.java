package com.finalproject;

import com.finalproject.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
@EnableJpaRepositories("com.finalproject.repository")
@EntityScan("com.FinalProject.model")
public class FinalProjectApplication {



    private static ComputerService computerService;
    @Autowired
    public FinalProjectApplication(ComputerService computerService) {
        FinalProjectApplication.computerService = computerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
        for (int i =0; i<9; i++) {
            computerService.createDefaultComputer();
        }
    }

}
