package com.finalproject.repository;

import com.finalproject.model.Invoice;
import com.finalproject.model.InvoiceStatus;
import com.finalproject.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, String> {

    Invoice getByPerson_IdAndCheckedFalse(String id);

    List<Invoice> getByPersonAndCheckedTrueOrderByDateCreatedAsc(Person person);

    List<Invoice> findByCheckedTrueAndInvoiceStatusOrderByDateCreatedAsc(InvoiceStatus invoiceStatus);
}
