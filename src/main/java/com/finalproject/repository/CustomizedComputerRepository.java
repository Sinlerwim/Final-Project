package com.finalproject.repository;

import com.finalproject.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomizedComputerRepository {
    Page<Computer> findByCriteria(String operatingSystem, Pageable page);
}
