package com.finalproject.controller;
import com.finalproject.model.Computer;
import com.finalproject.model.Person;
import com.finalproject.service.*;
import com.finalproject.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/delete")
public class DeleteController {
    private final ComputerService computerService;

    private final ProcessorService processorService;

    private final VideoCardService videoCardService;

    private final DiskDriveService diskDriveService;

    private final PersonService personService;

    private final ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteController.class);

    @Autowired
    public DeleteController(ComputerService computerService, ProcessorService processorService, VideoCardService videoCardService, DiskDriveService diskDriveService, PersonService personService, ImageService imageService) {
        this.computerService = computerService;
        this.processorService = processorService;
        this.videoCardService = videoCardService;
        this.diskDriveService = diskDriveService;
        this.personService = personService;
        this.imageService = imageService;
    }

    @GetMapping("/computer/{id}")
    public ModelAndView getComputerDelete(@PathVariable("id") String id, ModelAndView modelAndView) {
        final Computer computer = computerService.findById(id);
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("computer", computer);
        modelAndView.setViewName("deleteComputer");
        return modelAndView;
    }

    @PostMapping("/computer/{id}")
    public ModelAndView deleteComputer(@PathVariable("id") String id) {
        computerService.delete(id);
        LOGGER.info("Deleted computer: " + id);
        return new ModelAndView("redirect:/computers?page=1");
    }

    @GetMapping("/image/{computerId}/{imageId}")
    public ModelAndView deleteImage(@PathVariable("computerId") String computerId, @PathVariable("imageId") String imageId) {
        imageService.deleteImageById(imageId);
        return new ModelAndView("redirect:/update/computer/" + computerId);
    }

    @PostMapping("/processor/{id}")
    public ModelAndView deleteProcessor(@PathVariable("id") String id) {
        processorService.delete(id);
        LOGGER.info("Deleted processor: " + id);
        return new ModelAndView("redirect:/processor/create");
    }

    @PostMapping("/video-card/{id}")
    public ModelAndView deleteVideoCard(@PathVariable("id") String id) {
        videoCardService.delete(id);
        LOGGER.info("Deleted video card: " + id);
        return new ModelAndView("redirect:/processor/create");
    }

    @PostMapping("/disk-drive/{id}")
    public ModelAndView deleteDiskDrive(@PathVariable("id") String id) {
        diskDriveService.delete(id);
        LOGGER.info("Deleted disk drive: " + id);
        return new ModelAndView("redirect:/processor/create");
    }

    @PostMapping("/user/{id}")
    public ModelAndView deleteUser(@PathVariable("id") String id) {
        Person currentPerson = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!currentPerson.getId().equals(id)) {
            personService.deleteById(id);
            LOGGER.info("Deleted person: " + id + "\n by " + currentPerson);
        }
        return new ModelAndView("redirect:/update/users");
    }
}
