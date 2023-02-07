package com.finalproject.controller;

import com.finalproject.model.Person;
import com.finalproject.model.Role;
import com.finalproject.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{id}")
    public ModelAndView getPersonalDataAsAdmin(@PathVariable("id") String id, ModelAndView modelAndView) {
        final Person person = personService.getById(id);
        modelAndView.addObject("person", person);
        modelAndView.setViewName("adminUserInfo");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user-update")
    public ModelAndView updatePersonAsAdmin(@ModelAttribute @Valid Person person,
                                            ModelAndView modelAndView, BindingResult bindingResult) {
        Person currentPerson = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("person", person);
            modelAndView.setViewName("adminUserInfo");
            return modelAndView;
        }
        if (!currentPerson.getId().equals(person.getId())) {
            personService.update(person);
        } else {
            person.setRole(currentPerson.getRole());
            personService.update(person);
        }
        LOGGER.info("Updated user: " + person);
        return new ModelAndView("redirect:/user-update" + person.getId());
    }

    @GetMapping("/users")
    public ModelAndView getUsers(ModelAndView modelAndView) {
        List<Person> users = personService.getAllByRole(Role.USER);
        List<Person> admins = personService.getAllByRole(Role.ADMIN);
        modelAndView.addObject("users", users);
        modelAndView.addObject("admins", admins);
        modelAndView.setViewName("adminUsers");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") String id) {
        Person currentPerson = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentPerson.getId().equals(id)) {
            personService.deleteById(id);
            LOGGER.info("Deleted person: " + id + "\n by " + currentPerson);
        }
        return new ModelAndView("redirect:/user-update");
    }
}
