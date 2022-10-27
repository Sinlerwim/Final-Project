package com.finalproject.service;

import com.finalproject.model.Invoice;
import com.finalproject.model.Person;
import com.finalproject.model.Role;
import com.finalproject.repository.InvoiceRepository;
import com.finalproject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;
    private final InvoiceRepository invoiceRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, InvoiceRepository invoiceRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.invoiceRepository = invoiceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findPersonByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void save(Person person) {
        if (person.getPassword() == null) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        if (personRepository.findPersonByEmail(person.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        if(person.getId() != null && personRepository.existsById(person.getId())) {
            personRepository.save(person);
        } else {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            person.setRole(person.getEmail().equalsIgnoreCase("admin@admin.com") ? Role.ADMIN : Role.USER);
            Invoice invoice = new Invoice();
            invoice.setPerson(person);
            personRepository.save(person);
            invoiceRepository.save(invoice);
        }
    }

    public void update(Person person) {
        if (person.getPassword() == null) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        personRepository.save(person);
    }

    public Person getById(String id) {
        return personRepository.findById(id).get();
    }

    public List<Person> getAllByRole(Role role) {
        return personRepository.getByRoleOrderByEmailAsc(role);
    }

    public void deleteById(String id) {
        personRepository.deleteById(id);
    }
}
