package com.finalproject.controller;


import com.finalproject.dto.ComputerCreationDTO;
import com.finalproject.dto.ComputerUpdateDTO;
import com.finalproject.mapper.ComputerMapper;
import com.finalproject.model.Computer;
import com.finalproject.service.*;
import com.finalproject.util.ConverterUtil;
import com.finalproject.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/computers")
public class ComputerController {

    private final ComputerService computerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);
    private final VideoCardService videoCardService;
    private final DiskDriveService diskDriveService;
    private final ProcessorService processorService;
    private final InvoiceService invoiceService;


    @Autowired
    public ComputerController(ComputerService computerService, ProcessorService processorService,
                              VideoCardService videoCardService, DiskDriveService diskDriveService, InvoiceService invoiceService) {
        this.computerService = computerService;
        this.processorService = processorService;
        this.videoCardService = videoCardService;
        this.diskDriveService = diskDriveService;
        this.invoiceService = invoiceService;
    }

    @GetMapping(params = {"page"})
    public ModelAndView getAll(@RequestParam("page") int numberOfPage, ModelAndView modelAndView) {
        final Page<Computer> page = computerService.findPage(numberOfPage - 1);
        modelAndView.addObject("converterUtil", new ConverterUtil());
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("posts", page);
        modelAndView.setViewName("computers");
        return modelAndView;
    }

    @GetMapping("/computer/{id}")
    public ModelAndView getById(@PathVariable("id") String id, ModelAndView modelAndView) {
        Computer computer = computerService.findById(id);
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("computer", computer);
        modelAndView.setViewName("computer");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update/{id}")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public ModelAndView getComputerDelete(@PathVariable("id") String id, ModelAndView modelAndView) {
        final Computer computer = computerService.findById(id);
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("computer", computer);
        modelAndView.setViewName("deleteComputer");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{id}")
    public ModelAndView deleteComputer(@PathVariable("id") String id) {
        if (invoiceService.isComputerUsedInInvoicesById(id)) {

        }
        computerService.delete(id);
        LOGGER.info("Deleted computer: " + id);
        return new ModelAndView("redirect:/computers?page=1");
    }

    @GetMapping("/get-nine")
    public Iterable<Computer> getRandomNine() {
        return computerService.getRandomN(9);
    }

    @GetMapping("/create-default")
    public String createDefaultComputer() {
        return computerService.createDefaultComputer();
    }

//    @GetMapping("/ids")
//    public Iterable<String> getAllIds() {
//        return computerService.getAllIds();
//    }
//
//    @GetMapping("/model")
//    public ComputerDTO findByModel(@RequestParam String model) {
//        return computerService.findByModel(model);
//    }

//    @PostMapping
//    public String create(@RequestBody ComputerDTO computerDTO) {
//        return computerService.create(computerDTO);
//    }

//    @PutMapping("/update/{id}")
//    public ComputerDTO update(@PathVariable String id, @RequestBody ComputerDTO computerDTO) {
//        return computerService.update(id, computerDTO);
//    }

//    @DeleteMapping
//    public void delete(@RequestParam String id) {
//        computerService.delete(id);
//    }
}
