package com.FinalProject.controller;


import com.FinalProject.config.ImageUtil;
import com.FinalProject.dto.ComputerDTO;
import com.FinalProject.mapper.ComputerMapper;
import com.FinalProject.model.*;
import com.FinalProject.service.ComputerService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/computers")
public class ComputerController {

    private final ComputerService computerService;

    @Autowired
    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping(params = {"page"})
    public ModelAndView getAll(@RequestParam("page") int numberOfPage, ModelAndView modelAndView) {
        final Page<Computer> page = computerService.findPage(numberOfPage);
//        final List<ComputerDTO> computers = page.getContent().stream().map(ComputerMapper::toDTO).collect(Collectors.toList());
//        final int totalPages = page.getTotalPages();
//        modelAndView.addObject("totalPages", totalPages);
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("posts", page);
        modelAndView.setViewName("computers");
        return modelAndView;
    }

//    @GetMapping("/computer")
//    public ModelAndView get(ModelAndView modelAndView) {
//        final List<Computer> computers = IterableUtils.toList(computerService.getRandomN(9));
//        modelAndView.addObject("imageUtil", new ImageUtil());
//        modelAndView.addObject("computers", computers);
//        modelAndView.setViewName("computer");
//        return modelAndView;
//    }

    @GetMapping("/computer/{id}")
    public ModelAndView getById(@PathVariable("id") String id, ModelAndView modelAndView) {
        ComputerDTO computerDTO = computerService.findById(id);
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("computer", computerDTO);
        modelAndView.setViewName("computer");
        return modelAndView;
    }

    @GetMapping("/get-nine")
    public Iterable<Computer> getRandomNine() {
        return computerService.getRandomN(9);
    }

    @GetMapping("/create-default")
    public String createDefaultComputer() {
        return computerService.createDefaultComputer();
    }

    @GetMapping("/ids")
    public Iterable<String> getAllIds() {
        return computerService.getAllIds();
    }

    @GetMapping("/model")
    public ComputerDTO findByModel(@RequestParam String model) {
        return computerService.findByModel(model);
    }

    @PostMapping
    public String create(@RequestBody ComputerDTO computerDTO) {
        return computerService.create(computerDTO);
    }

    @PutMapping("/update/{id}")
    public ComputerDTO update(@PathVariable String id, @RequestBody ComputerDTO computerDTO) {
        return computerService.update(id, computerDTO);
    }

    @DeleteMapping
    public void delete(@RequestParam String id) {
        computerService.delete(id);
    }
}
