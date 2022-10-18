package com.finalproject.controller;

import com.finalproject.config.ImageUtil;
import com.finalproject.dto.ComputerCreationDTO;
import com.finalproject.dto.ComputerUpdateDTO;
import com.finalproject.mapper.ComputerMapper;
import com.finalproject.model.Computer;
import com.finalproject.model.DiskDrive;
import com.finalproject.model.Processor;
import com.finalproject.model.VideoCard;
import com.finalproject.service.ComputerService;
import com.finalproject.service.DiskDriveService;
import com.finalproject.service.ProcessorService;
import com.finalproject.service.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/update")
public class UpdateController {

    private final ComputerService computerService;

    private final ProcessorService processorService;

    private final VideoCardService videoCardService;

    private final DiskDriveService diskDriveService;


    @Autowired
    public UpdateController(ComputerService computerService, ProcessorService processorService,
                            VideoCardService videoCardService, DiskDriveService diskDriveService) {
        this.computerService = computerService;
        this.processorService = processorService;
        this.videoCardService = videoCardService;
        this.diskDriveService = diskDriveService;
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
            return new ModelAndView("redirect:/create/disk-drive");
        }
    }
}
