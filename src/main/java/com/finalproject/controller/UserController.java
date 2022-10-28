package com.finalproject.controller;

import com.finalproject.model.Person;
import com.finalproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    private final PersonService personService;

    @Autowired
    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/registration")
    public ModelAndView getRegistration(ModelAndView modelAndView) {
        final Person person = new Person();
        modelAndView.addObject("person", person);
        modelAndView.setViewName("registration");
        return modelAndView;
    }


    @PostMapping("/registration")
    public ModelAndView registerNewPerson(@ModelAttribute @Valid Person person,
                                          ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("person", person);
            modelAndView.setViewName("registration");
            return modelAndView;
        } else {
            personService.save(person);
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/login")
    public ModelAndView getLogin(ModelAndView modelAndView) {
        final Person person = new Person();
        modelAndView.addObject("person", person);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/personal-data")
    public ModelAndView getPersonalData(ModelAndView modelAndView) {
        final Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("person", person);
        modelAndView.setViewName("userInfo");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/personal-data")
    public ModelAndView updatePerson(@ModelAttribute @Valid Person person,
                                     ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("person", person);
            modelAndView.setViewName("userInfo");
            return modelAndView;
        } else {
            Person currentPerson = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            currentPerson.setName(person.getName());
            currentPerson.setAddress(person.getAddress());
            currentPerson.setCity(person.getCity());
            currentPerson.setEmail(person.getEmail());
            currentPerson.setPassword(person.getPassword());
            currentPerson.setPhoneNumber(person.getPhoneNumber());
            personService.update(currentPerson);
            return new ModelAndView("redirect:/personal-data");
        }
    }
}
