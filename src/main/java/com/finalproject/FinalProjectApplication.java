package com.finalproject;

import com.finalproject.service.ComputerService;
import com.finalproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
@EnableJpaRepositories("com.finalproject.repository")
@EntityScan("com.finalproject.model")
public class FinalProjectApplication {



//    private static ComputerService computerService;
//
//    private static PersonService personService;
//    @Autowired
//    public FinalProjectApplication(ComputerService computerService, PersonService personService) {
//        FinalProjectApplication.computerService = computerService;
//        FinalProjectApplication.personService = personService;
//    }

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
//        for (int i =0; i<9; i++) {
//            computerService.createDefaultComputer();
//        }
//        Person admin = new Person();
//        admin.setPassword("admin");
//        admin.setEmail("admin@admin.com");
//        admin.setCity("admin");
//        admin.setPhoneNumber("admin");
//        admin.setAddress("admin");
//        admin.setName("admin");
//        personService.save(admin);
    }

}
