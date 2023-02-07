package com.finalproject.controller;

import com.finalproject.dto.ProcessorCreationDTO;
import com.finalproject.mapper.ProcessorMapper;
import com.finalproject.model.Processor;
import com.finalproject.service.ProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/processors")
public class ProcessorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorController.class);
    private final ProcessorService processorService;

    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }


    @GetMapping("/create")
    public ModelAndView getProcessorCreation(ModelAndView modelAndView) {
        ProcessorCreationDTO processorCreationDTO = new ProcessorCreationDTO();
        modelAndView.addObject("processor", processorCreationDTO);
        modelAndView.addObject("processors", processorService.findAll());
        modelAndView.setViewName("createProcessor");
        return modelAndView;
    }


    @PostMapping("/create")
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
            return new ModelAndView("redirect:/processors/create");
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView getProcessorUpdate(@PathVariable("id") String id, ModelAndView modelAndView) {
        Processor processor = processorService.findById(id);
        modelAndView.addObject("processor", processor);
        modelAndView.addObject("processors", processorService.findAll());
        modelAndView.setViewName("updateProcessor");
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateProcessor(@ModelAttribute @Valid Processor processor, ModelAndView modelAndView,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("processor", processor);
            modelAndView.setViewName("updateComputer");
            return modelAndView;
        } else {
            String id = processorService.save(processor);
            LOGGER.info("Updated processor: " + processor);
            return new ModelAndView("redirect:/processors/create");
        }
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteProcessor(@PathVariable("id") String id) {
        processorService.delete(id);
        LOGGER.info("Deleted processor: " + id);
        return new ModelAndView("redirect:/processors/create");
    }

}
