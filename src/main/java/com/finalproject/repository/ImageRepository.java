package com.finalproject.repository;

import com.finalproject.model.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image,String> {
    @Query("select i from Image i where i.computer.id = ?1")
    List<Image> findByComputer_Id(String id);


}
