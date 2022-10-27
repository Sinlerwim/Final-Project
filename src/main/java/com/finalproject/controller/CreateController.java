package com.finalproject.controller;

import com.finalproject.dto.ComputerCreationDTO;
import com.finalproject.dto.DiskDriveCreationDTO;
import com.finalproject.dto.ProcessorCreationDTO;
import com.finalproject.dto.VideoCardCreationDTO;
import com.finalproject.mapper.ComputerMapper;
import com.finalproject.mapper.DiskDriveMapper;
import com.finalproject.mapper.ProcessorMapper;
import com.finalproject.mapper.VideoCardMapper;
import com.finalproject.model.Computer;
import com.finalproject.model.DiskDrive;
import com.finalproject.model.Processor;
import com.finalproject.model.VideoCard;
import com.finalproject.service.ComputerService;
import com.finalproject.service.DiskDriveService;
import com.finalproject.service.ProcessorService;
import com.finalproject.service.VideoCardService;
import com.finalproject.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/create")
public class CreateController {

    private final ComputerService computerService;

    private final ProcessorService processorService;

    private final VideoCardService videoCardService;

    private final DiskDriveService diskDriveService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateController.class);


    @Autowired
    public CreateController(ComputerService computerService, ProcessorService processorService,
                            VideoCardService videoCardService, DiskDriveService diskDriveService) {
        this.computerService = computerService;
        this.processorService = processorService;
        this.videoCardService = videoCardService;
        this.diskDriveService = diskDriveService;
    }

    @GetMapping("/computer")
    public ModelAndView getComputerCreation(ModelAndView modelAndView) {
        ComputerCreationDTO computerCreationDTO = new ComputerCreationDTO();
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("computer", computerCreationDTO);
        modelAndView.addObject("processors", processorService.findAll());
        modelAndView.addObject("videoCards", videoCardService.findAll());
        modelAndView.addObject("diskDrives", diskDriveService.findAll());
        modelAndView.setViewName("createComputer");
        return modelAndView;
    }

    @PostMapping("/computer")
    public ModelAndView createComputer(@ModelAttribute("ComputerCreationDTO") @Valid ComputerCreationDTO computerCreationDTO, ModelAndView modelAndView,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("computer", computerCreationDTO);
            modelAndView.setViewName("createComputer");
            return modelAndView;
        } else {
            Computer computer = ComputerMapper.fromComputerCreationDTO(computerCreationDTO);
            String id = computerService.save(computer);
            LOGGER.info("Created computer: " + computer);
            return new ModelAndView("redirect:/computers/computer/" + id);
        }
    }

    @GetMapping("/processor")
    public ModelAndView getProcessorCreation(ModelAndView modelAndView) {
        ProcessorCreationDTO processorCreationDTO = new ProcessorCreationDTO();
        modelAndView.addObject("processor", processorCreationDTO);
        modelAndView.addObject("processors", processorService.findAll());
        modelAndView.setViewName("createProcessor");
        return modelAndView;
    }

    @PostMapping("/processor")
    public ModelAndView createProcessor(@ModelAttribute @Valid ProcessorCreationDTO processorCreationDTO,
                                        ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("processor", processorCreationDTO);
            modelAndView.setViewName("createProcessor");
            return modelAndView;
        } else {
            Processor processor = ProcessorMapper.fromProcessorCreationDTO(processorCreationDTO);
            processorService.save(processor);
            LOGGER.info("Created processor: " + processor);
            return new ModelAndView("redirect:/create/processor");
        }
    }

    @GetMapping("/video-card")
    public ModelAndView getVideoCardCreation(ModelAndView modelAndView) {
        VideoCardCreationDTO videoCardCreationDTO = new VideoCardCreationDTO();
        modelAndView.addObject("videoCard", videoCardCreationDTO);
        modelAndView.addObject("videoCards", videoCardService.findAll());
        modelAndView.setViewName("createVideoCard");
        return modelAndView;
    }

    @PostMapping("/video-card")
    public ModelAndView createVideoCard(@ModelAttribute @Valid VideoCardCreationDTO videoCardCreationDTO,
                                        ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("videoCard", videoCardCreationDTO);
            modelAndView.setViewName("createVideoCard");
            return modelAndView;
        } else {
            VideoCard videoCard = VideoCardMapper.fromVideoCardCreationDTO(videoCardCreationDTO);
            videoCardService.save(videoCard);
            LOGGER.info("Created video card: " + videoCard);
            return new ModelAndView("redirect:/create/video-card");
        }
    }

    @GetMapping("/disk-drive")
    public ModelAndView getDiskDriveCreation(ModelAndView modelAndView) {
        DiskDriveCreationDTO diskDriveCreationDTO = new DiskDriveCreationDTO();
        modelAndView.addObject("diskDrive", diskDriveCreationDTO);
        modelAndView.addObject("diskDrives", diskDriveService.findAll());
        modelAndView.setViewName("createDiskDrive");
        return modelAndView;
    }

    @PostMapping("/disk-drive")
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
            return new ModelAndView("redirect:/create/disk-drive");
        }
    }
}
