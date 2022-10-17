package com.finalproject.mapper;

import com.finalproject.dto.ProcessorCreationDTO;
import com.finalproject.model.Processor;

public class ProcessorMapper {

    public static Processor fromProcessorCreationDTO(ProcessorCreationDTO processorCreationDTO) {
        final Processor processor = new Processor();
        processor.setManufacturer(processorCreationDTO.getManufacturer());
        processor.setModel(processorCreationDTO.getModel());
        processor.setFrequency(processorCreationDTO.getFrequency());
        return processor;
    }
}
