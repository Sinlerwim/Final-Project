package com.finalproject.service;


import com.finalproject.model.Invoice;
import com.finalproject.model.InvoiceStatus;
import com.finalproject.model.Person;
import com.finalproject.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice getCurrentInvoiceByUserId(String id) {
        return invoiceRepository.getByPerson_IdAndCheckedFalse(id);
    }

    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void checkAndSave(Invoice invoice) {
        if (!invoice.getProducts().isEmpty()) {
            invoice.setChecked(true);
            invoice.setDateCreated(LocalDate.now());
            invoice.setInvoiceStatus(InvoiceStatus.REGISTERED);
            invoiceRepository.save(invoice);
            Invoice newEmptyInvoice = new Invoice();
            newEmptyInvoice.setPerson(invoice.getPerson());
            invoiceRepository.save(newEmptyInvoice);
        }

    }

    public List<Invoice> getCheckedInvoicesByPerson(Person person) {
        return invoiceRepository.getByPersonAndCheckedTrueOrderByDateCreatedAsc(person);
    }

    public List<Invoice> getCheckedInvoicesByStatusAndSortedByDate(InvoiceStatus invoiceStatus) {
        return invoiceRepository.findByCheckedTrueAndInvoiceStatusOrderByDateCreatedAsc(invoiceStatus);
    }

    public Invoice getById(String id) {
        return invoiceRepository.findById(id).get();
    }
}
