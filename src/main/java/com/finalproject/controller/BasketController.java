package com.finalproject.controller;

import com.finalproject.model.Computer;
import com.finalproject.model.Invoice;
import com.finalproject.model.Person;
import com.finalproject.service.ComputerService;
import com.finalproject.service.InvoiceService;
import com.finalproject.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/basket")
public class BasketController {
    private final InvoiceService invoiceService;

    private final ComputerService computerService;

    @Autowired
    public BasketController(InvoiceService invoiceService, ComputerService computerService) {
        this.invoiceService = invoiceService;
        this.computerService = computerService;
    }

    @GetMapping()
    public ModelAndView getBasket(ModelAndView modelAndView) {
        Invoice invoice = getUserBasket();
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("invoice", invoice);
        modelAndView.setViewName("basket");
        return modelAndView;
    }

    @GetMapping("/add-computer")
    public ModelAndView addComputerToBasket(@RequestParam(name = "id") String computerId, @RequestParam String from) {
        Invoice invoice = getUserBasket();
        Computer computer = computerService.findById(computerId);
        invoice.addProduct(computer);
        invoiceService.save(invoice);
        if (from.equals("computer")) {
            return new ModelAndView("redirect:/computers/computer/" + computerId);
        } else {
            return new ModelAndView("redirect:/basket");
        }
    }

    @GetMapping("/delete")
    public ModelAndView deleteComputerFromBasket(@RequestParam(name = "id") String computerId) {
        Invoice invoice = getUserBasket();
        Computer computer = computerService.findById(computerId);
        invoice.deleteProduct(computer);
        invoiceService.save(invoice);
        return new ModelAndView("redirect:/basket");
    }

    @GetMapping("/decrease")
    public ModelAndView decreaseNumberOfComputersInBasket(@RequestParam(name = "id") String computerId) {
        Invoice invoice = getUserBasket();
        Computer computer = computerService.findById(computerId);
        invoice.decreaseProduct(computer);
        invoiceService.save(invoice);
        return new ModelAndView("redirect:/basket");
    }

    @GetMapping("/confirm")
    public ModelAndView confirmOrder() {
        Invoice invoice = getUserBasket();
        invoiceService.checkAndSave(invoice);
        return new ModelAndView("redirect:/basket");
    }

    private Invoice getUserBasket() {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return invoiceService.getCurrentInvoiceByUserId(person.getId());
    }
}
