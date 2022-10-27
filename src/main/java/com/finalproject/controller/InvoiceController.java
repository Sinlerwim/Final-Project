package com.finalproject.controller;

import com.finalproject.model.Invoice;
import com.finalproject.model.InvoiceStatus;
import com.finalproject.model.Person;
import com.finalproject.service.InvoiceService;
import com.finalproject.util.ImageUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ModelAndView getInvoiceAsUser(ModelAndView modelAndView) {
        final List<Invoice> checkedInvoicesByPerson = invoiceService.getCheckedInvoicesByPerson(getPrincipalPerson());
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("invoices", checkedInvoicesByPerson);
        modelAndView.setViewName("userInvoices");
        return modelAndView;
    }

    private Person getPrincipalPerson() {
        return (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ModelAndView getInvoicesAsAdmin(ModelAndView modelAndView) {
        final List<Invoice> registeredInvoices = invoiceService.getCheckedInvoicesByStatusAndSortedByDate(InvoiceStatus.REGISTERED);
        final List<Invoice> processedInvoices = invoiceService.getCheckedInvoicesByStatusAndSortedByDate(InvoiceStatus.PROCESSED);
        final List<Invoice> shippedInvoices = invoiceService.getCheckedInvoicesByStatusAndSortedByDate(InvoiceStatus.SHIPPED);
        final List<Invoice> enRouteInvoices = invoiceService.getCheckedInvoicesByStatusAndSortedByDate(InvoiceStatus.EN_ROUTE);
        final List<Invoice> arrivedInvoices = invoiceService.getCheckedInvoicesByStatusAndSortedByDate(InvoiceStatus.ARRIVED);
        modelAndView.addObject("registeredInvoices", registeredInvoices);
        modelAndView.addObject("processedInvoices", processedInvoices);
        modelAndView.addObject("shippedInvoices", shippedInvoices);
        modelAndView.addObject("enRouteInvoices", enRouteInvoices);
        modelAndView.addObject("arrivedInvoices", arrivedInvoices);
        modelAndView.setViewName("adminInvoices");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/invoice/{id}")
    public ModelAndView getInvoiceById(@PathVariable("id") String id, ModelAndView modelAndView) {
        final Invoice invoice = invoiceService.getById(id);
        modelAndView.addObject("imageUtil", new ImageUtil());
        modelAndView.addObject("invoice", invoice);
        modelAndView.setViewName("invoice");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/invoice/set-status")
    public ModelAndView setInvoiceStatus(
            @RequestParam String invoiceStatus,
            @RequestParam String id,
            ModelAndView modelAndView) {
        final Invoice invoice = invoiceService.getById(id);
        invoice.setInvoiceStatus(InvoiceStatus.valueOf(invoiceStatus));
        invoiceService.save(invoice);
        return new ModelAndView("redirect:/invoices/admin/invoice/" + id);
    }
}
