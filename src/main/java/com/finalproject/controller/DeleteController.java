package com.finalproject.controller;

import com.finalproject.config.ImageUtil;
import com.finalproject.model.Computer;
import com.finalproject.service.ComputerService;
import com.finalproject.service.DiskDriveService;
import com.finalproject.service.ProcessorService;
import com.finalproject.service.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/delete")
public class DeleteController {
    private final ComputerService computerService;

    private final ProcessorService processorService;

    private final VideoCardService videoCardService;

    private final DiskDriveService diskDriveService;

    @Autowired
    public DeleteController(ComputerService computerService, ProcessorService processorService, VideoCardService videoCardService, DiskDriveService diskDriveService) {
        this.computerService = computerService;
        this.processorService = processorService;
        this.videoCardService = videoCardService;
        this.diskDriveService = diskDriveService;
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
        return new ModelAndView("redirect:/computers?page=1");
    }

    @PostMapping("/processor/{id}")
    public ModelAndView deleteProcessor(@PathVariable("id") String id) {
        processorService.delete(id);
        return new ModelAndView("redirect:/processor/create");
    }

    @PostMapping("/video-card/{id}")
    public ModelAndView deleteVideoCard(@PathVariable("id") String id) {
        videoCardService.delete(id);
        return new ModelAndView("redirect:/processor/create");
    }

    @PostMapping("/disk-drive/{id}")
    public ModelAndView deleteDiskDrive(@PathVariable("id") String id) {
        diskDriveService.delete(id);
        return new ModelAndView("redirect:/processor/create");
    }
}
