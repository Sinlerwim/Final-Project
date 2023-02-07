package com.finalproject.controller;


import com.finalproject.dto.DiskDriveCreationDTO;
import com.finalproject.mapper.DiskDriveMapper;
import com.finalproject.model.DiskDrive;
import com.finalproject.service.DiskDriveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/disk-drives")
@Controller
public class DiskDriveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiskDriveController.class);

    private final DiskDriveService diskDriveService;

    public DiskDriveController(DiskDriveService diskDriveService) {
        this.diskDriveService = diskDriveService;
    }

    @GetMapping("/create")
    public ModelAndView getDiskDriveCreation(ModelAndView modelAndView) {
        DiskDriveCreationDTO diskDriveCreationDTO = new DiskDriveCreationDTO();
        modelAndView.addObject("diskDrive", diskDriveCreationDTO);
        modelAndView.addObject("diskDrives", diskDriveService.findAll());
        modelAndView.setViewName("createDiskDrive");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createDiskDrive(@ModelAttribute @Valid DiskDriveCreationDTO diskDriveCreationDTO,
                                        ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("diskDrive", diskDriveCreationDTO);
            modelAndView.setViewName("createDiskDrive");
            return modelAndView;
        } else {
            DiskDrive diskDrive = DiskDriveMapper.fromDiskDriveCreationDTO(diskDriveCreationDTO);
            diskDriveService.save(diskDrive);
            LOGGER.info("Created disk drive: " + diskDrive);
            return new ModelAndView("redirect:/disk-drives/create");
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView getDiskDriveUpdate(@PathVariable("id") String id, ModelAndView modelAndView) {
        DiskDrive diskDrive = diskDriveService.findById(id);
        modelAndView.addObject("diskDrive", diskDrive);
        modelAndView.addObject("diskDrives", diskDriveService.findAll());
        modelAndView.setViewName("updateDiskDrive");
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateDiskDrive(@ModelAttribute @Valid DiskDrive diskDrive, ModelAndView modelAndView,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("diskDrive", diskDrive);
            modelAndView.setViewName("updateDiskDrive");
            return modelAndView;
        } else {
            String id = diskDriveService.save(diskDrive);
            LOGGER.info("Updated disk drive: " + diskDrive);
            return new ModelAndView("redirect:/disk-drives/create");
        }
    }

    @PostMapping("/disk-drive/{id}")
    public ModelAndView deleteDiskDrive(@PathVariable("id") String id) {
        diskDriveService.delete(id);
        LOGGER.info("Deleted disk drive: " + id);
        return new ModelAndView("redirect:/processor/create");
    }
}
