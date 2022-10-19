package com.finalproject.service;


import com.finalproject.model.Processor;
import com.finalproject.repository.ProcessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {


    private final ProcessorRepository processorRepository;

    @Autowired
    public ProcessorService(ProcessorRepository processorRepository) {
        this.processorRepository = processorRepository;
    }

    public Processor saveOrReturnIfExist(Processor processor) {
        if(processorRepository.existsByManufacturerAndModelAndFrequencyAllIgnoreCase(processor.getManufacturer(),
                processor.getModel(), processor.getFrequency())) {
//            System.out.println("Processor exist. Returning...");
            return processorRepository.findFirstByManufacturerAndModelAndFrequencyAllIgnoreCase(processor.getManufacturer(),
                    processor.getModel(), processor.getFrequency());
        } else {
            return processorRepository.save(processor);
        }
    }

    public Iterable<Processor> findAll() {
        return processorRepository.findAll();
    }

    public Processor findById(String id) {
        if(id.isEmpty()){
            return null;
        }
        return processorRepository.findById(id).get();
    }

    public String save(Processor processor) {
        return processorRepository.save(processor).getId();
    }

    public void delete(String id) {
        if (processorRepository.existsById(id)) {
            processorRepository.deleteById(id);
        }
    }
}
