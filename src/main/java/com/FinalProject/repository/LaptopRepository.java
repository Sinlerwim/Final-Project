package com.FinalProject.repository;

import com.FinalProject.model.Laptop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LaptopRepository extends CrudRepository<Laptop, String> {
}
