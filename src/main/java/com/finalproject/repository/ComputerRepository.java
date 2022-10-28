package com.finalproject.repository;

import com.finalproject.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, String>, CustomizedComputerRepository {
    Optional<Computer> findByModel(String model);

    @Query(value = "SELECT * FROM public.computer order by random() desc limit :limit", nativeQuery = true)
    List<Computer> getRandomN(@Param("limit") int limit);

    @Query(value = "SELECT DISTINCT operating_system FROM public.computer", nativeQuery = true)
    List<String> getAllOperatingSystems();
}
