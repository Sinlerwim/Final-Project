package com.finalproject.controller;

import com.finalproject.dto.ComputerUpdateDTO;
import com.finalproject.mapper.ComputerMapper;
import com.finalproject.model.*;
import com.finalproject.service.*;
import com.finalproject.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/update")
public class UpdateController {

    private final ComputerService computerService;

    private final ProcessorService processorService;

    private final VideoCardService videoCardService;

    private final DiskDriveService diskDriveService;

    private final PersonService personService;

    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateController.class);


    @Autowired
    public UpdateController(ComputerService computerService, ProcessorService processorService,
                            VideoCardService videoCardService, DiskDriveService diskDriveService, PersonService personService, PasswordEncoder passwordEncoder) {
        this.computerService = computerService;
        this.processorService = processorService;
        this.videoCardService = videoCardService;
        this.diskDriveService = diskDriveService;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/computer/{id}")
    public ModelAndView getComputerUpdate(@PathVariable("id") String id, ModelAndView modelAndView) {
        ComputerUpdateDTO computerUpdateDTO = ComputerMapper.toComputerUpdateDTO(computerService.findById(id));
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("computer", computerUpdateDTO);
        modelAndView.addObject("processors", processorService.findAll());
        modelAndView.addObject("videoCards", videoCardService.findAll());
        modelAndView.addObject("diskDrives", diskDriveService.findAll());
        modelAndView.setViewName("updateComputer");
        return modelAndView;
    }

    @PostMapping("/computer")
    public ModelAndView updateComputer(@ModelAttribute @Valid ComputerUpdateDTO computerUpdateDTO, ModelAndView modelAndView,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("computer", computerUpdateDTO);
            modelAndView.setViewName("updateComputer");
            return modelAndView;
        } else {
            Computer computer = ComputerMapper.fromComputerUpdateDTO(computerUpdateDTO);
            String id = computerService.save(computer);
            LOGGER.info("Updated computer: " + computer);
            return new ModelAndView("redirect:/computers/computer/" + id);
        }
    }

    @GetMapping("/processor/{id}")
    public ModelAndView getProcessorUpdate(@PathVariable("id") String id, ModelAndView modelAndView) {
        Processor processor = processorService.findById(id);
        modelAndView.addObject("processor", processor);
        modelAndView.addObject("processors", processorService.findAll());
        modelAndView.setViewName("updateProcessor");
        return modelAndView;
    }

    @PostMapping("/processor/{id}")
    public ModelAndView updateProcessor(@ModelAttribute @Valid Processor processor, ModelAndView modelAndView,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("processor", processor);
            modelAndView.setViewName("updateComputer");
            return modelAndView;
        } else {
            String id = processorService.save(processor);
            LOGGER.info("Updated processor: " + processor);
            return new ModelAndView("redirect:/create/processor");
        }
    }

    @GetMapping("/video-card/{id}")
    public ModelAndView getVideoCardUpdate(@PathVariable("id") String id, ModelAndView modelAndView) {
        final VideoCard videoCard = videoCardService.findById(id);
        modelAndView.addObject("videoCard", videoCard);
        modelAndView.addObject("videoCards", videoCardService.findAll());
        modelAndView.setViewName("updateVideoCard");
        return modelAndView;
    }

    @PostMapping("/video-card/{id}")
    public ModelAndView updateVideoCard(@ModelAttribute @Valid VideoCard videoCard, ModelAndView modelAndView,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("videoCard", videoCard);
            modelAndView.setViewName("updateVideoCard");
            return modelAndView;
        } else {
            String id = videoCardService.save(videoCard);
            LOGGER.info("Updated video card: " + videoCard);
            return new ModelAndView("redirect:/create/video-card");
        }
    }

    @GetMapping("/disk-drive/{id}")
    public ModelAndView getDiskDriveUpdate(@PathVariable("id") String id, ModelAndView modelAndView) {
        DiskDrive diskDrive = diskDriveService.findById(id);
        modelAndView.addObject("diskDrive", diskDrive);
        modelAndView.addObject("diskDrives", diskDriveService.findAll());
        modelAndView.setViewName("updateDiskDrive");
        return modelAndView;
    }

    @PostMapping("/disk-drive/{id}")
    public ModelAndView updateDiskDrive(@ModelAttribute @Valid DiskDrive diskDrive, ModelAndView modelAndView,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("diskDrive", diskDrive);
            modelAndView.setViewName("updateDiskDrive");
            return modelAndView;
        } else {
            String id = diskDriveService.save(diskDrive);
            LOGGER.info("Updated disk drive: " + diskDrive);
            return new ModelAndView("redirect:/create/disk-drive");
        }
    }

    @GetMapping("/user/{id}")
    public ModelAndView getPersonalDataAsAdmin(@PathVariable("id") String id, ModelAndView modelAndView) {
        final Person person = personService.getById(id);
        modelAndView.addObject("person", person);
        modelAndView.setViewName("adminUserInfo");
        return modelAndView;
    }

    @PostMapping("/user")
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
        return new ModelAndView("redirect:/update/user/" + person.getId());
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
}
