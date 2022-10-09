package com.FinalProject.controller;


import com.FinalProject.config.ImageUtil;
import com.FinalProject.model.Computer;
import com.FinalProject.service.ComputerService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    private final ComputerService computerService;

    @Autowired
    public MainController(ComputerService computerService) {
        this.computerService = computerService;
    }

//    @ResponseBody
    @GetMapping
    public ModelAndView main(ModelAndView modelAndView) {
//        return "<div align=center>" +
//                "<h1>FinalProjectApplication</h1> <br>" +
//                "<a href=http://localhost:8080/computers>Get all computers</a> <br>" +
//                "<a href=http://localhost:8080/computers/get-five>Get 5 random</a> <br>" +
//                "<a href=http://localhost:8080/computers/model?model=>Find by model</a> <br>" +
//                "<a href=http://localhost:8080/computers/ids>Get all ids</a> <br>" +
//                "<a href=http://localhost:8080/computers/create-default>Create and save default computer</a> <br>" +
//                "<a href=http://localhost:8080/laptops>Get all laptops</a> <br>" +
//                "</div>";
        for (int i =0; i<9; i++) {
            computerService.createDefaultComputer();
        }
        final List<Computer> computers = IterableUtils.toList(computerService.getRandomN(9));
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("computers", computers);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
