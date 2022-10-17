package com.finalproject.repository;

import com.finalproject.model.Processor;
import com.finalproject.model.ProcessorManufacturer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface ProcessorRepository extends CrudRepository<Processor,String> {

    boolean existsByManufacturerAndModelAndFrequencyAllIgnoreCase(@NotNull ProcessorManufacturer manufacturer, @NotEmpty String model, String frequency);

    Processor findFirstByManufacturerAndModelAndFrequencyAllIgnoreCase(@NotNull ProcessorManufacturer manufacturer, @NotEmpty String model, String frequency);


    @Override
    Optional<Processor> findById(String id);
}
