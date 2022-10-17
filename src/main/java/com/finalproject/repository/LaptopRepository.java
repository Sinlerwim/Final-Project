package com.finalproject.repository;

import com.finalproject.model.Laptop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LaptopRepository extends CrudRepository<Laptop, String> {
}
