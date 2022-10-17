package com.finalproject.controller;


import com.finalproject.config.ConverterUtil;
import com.finalproject.config.ImageUtil;
import com.finalproject.dto.ComputerDTO;
import com.finalproject.model.*;
import com.finalproject.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        final Page<Computer> page = computerService.findPage(numberOfPage - 1);
        modelAndView.addObject("converterUtil", new ConverterUtil());
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("posts", page);
        modelAndView.setViewName("computers");
        return modelAndView;
    }

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

//    @PostMapping
//    public String create(@RequestBody ComputerDTO computerDTO) {
//        return computerService.create(computerDTO);
//    }

    @PutMapping("/update/{id}")
    public ComputerDTO update(@PathVariable String id, @RequestBody ComputerDTO computerDTO) {
        return computerService.update(id, computerDTO);
    }

    @DeleteMapping
    public void delete(@RequestParam String id) {
        computerService.delete(id);
    }
}
