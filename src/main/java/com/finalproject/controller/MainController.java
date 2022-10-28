package com.finalproject.controller;


import com.finalproject.model.Computer;
import com.finalproject.service.ComputerService;
import com.finalproject.util.ImageUtil;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    private final ComputerService computerService;

    @Autowired
    public MainController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping
    public ModelAndView main(ModelAndView modelAndView) {
        final List<Computer> computers = IterableUtils.toList(computerService.getRandomN(9));
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("computers", computers);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
