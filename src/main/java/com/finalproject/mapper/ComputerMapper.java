package com.finalproject.mapper;

import com.finalproject.dto.ComputerCreationDTO;
import com.finalproject.dto.ComputerDTO;
import com.finalproject.model.*;
import com.finalproject.service.DiskDriveService;
import com.finalproject.service.ProcessorService;
import com.finalproject.service.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public final class ComputerMapper {

    @Autowired
    private ComputerMapper(ProcessorService processorService, VideoCardService videoCardService, DiskDriveService diskDriveService) {
        ComputerMapper.processorService = processorService;
        ComputerMapper.videoCardService = videoCardService;
        ComputerMapper.diskDriveService = diskDriveService;
    }

    private static ProcessorService processorService;

    private static VideoCardService videoCardService;

    private static DiskDriveService diskDriveService;

    public static ComputerDTO toDTO(Computer computer) {
        final ComputerDTO computerDTO = new ComputerDTO();
        computerDTO.setId(computer.getId());
        computerDTO.setImages(computer.getImages().stream()
                .map(Image::getBytes)
                .collect(Collectors.toList()));
        computerDTO.setManufacturer(computer.getManufacturer());
        computerDTO.setModel(computer.getModel());
        computerDTO.setRam(computer.getRam());
        computerDTO.setRamType(computer.getRamType());
        computerDTO.setOperatingSystem(computer.getOperatingSystem());
        computerDTO.setDescription(computer.getDescription());
        computerDTO.setPrice(computer.getPrice());

        computerDTO.setProcessorManufacturer(computer.getProcessor().getManufacturer());
        computerDTO.setProcessorModel(computer.getProcessor().getModel());
        computerDTO.setProcessorFrequency(computer.getProcessor().getFrequency());

        computerDTO.setVideoCardManufacturer(computer.getVideoCard().getManufacturer());
        computerDTO.setVideoCardModel(computer.getVideoCard().getModel());
        computerDTO.setVideoCardType(computer.getVideoCard().getType());
        computerDTO.setVram(computer.getVideoCard().getVram());

        computerDTO.setDiskDriveName(computer.getDiskDrive().getName());
        computerDTO.setDiskDriveType(computer.getDiskDrive().getType());
        computerDTO.setDiskDriveCapacity(computer.getDiskDrive().getCapacity());
        return computerDTO;
    }

    public static Computer fromDTO(ComputerDTO computerDTO) {
        final Computer computer = new Computer();
        computer.setId(computerDTO.getId());
        computer.setManufacturer(computerDTO.getManufacturer());
        computer.setImages(
                computerDTO.getImages().stream()
                        .map((bytes) -> {
                            Image image = new Image();
                            image.setBytes(bytes);
                            return image;
                                }
                        ).collect(Collectors.toList()));
        computer.setModel(computerDTO.getModel());
        computer.setRam(computerDTO.getRam());
        computer.setRamType(computerDTO.getRamType());
        computer.setOperatingSystem(computerDTO.getOperatingSystem());
        computer.setPrice(computerDTO.getPrice());
        return computer;
    }

    public static Computer fromComputerCreationDTO(ComputerCreationDTO creationDTO) {
        Computer computer = new Computer();
        computer.setManufacturer(creationDTO.getManufacturer());
        computer.setModel(creationDTO.getModel());
        computer.setRam(creationDTO.getRam());
        computer.setRamType(creationDTO.getRamType());
        computer.setOperatingSystem(creationDTO.getOperatingSystem());
        computer.setPrice(creationDTO.getPrice());
        computer.setImages(creationDTO.getImage().stream().map(multipartFile -> {
            try {
                Image image = new Image();
                image.setBytes(multipartFile.getBytes());
                return image;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));

        computer.setProcessor(getProcessorFromCreationDTO(creationDTO));
        computer.setVideoCard(getVideoCardFromCreationDTO(creationDTO));
        computer.setDiskDrive(getDiskDriveFromCreationDTO(creationDTO));
        return computer;
    }

    private static DiskDrive getDiskDriveFromCreationDTO(ComputerCreationDTO creationDTO) {
        return diskDriveService.findById(creationDTO.getDiskDriveId());
    }

    private static VideoCard getVideoCardFromCreationDTO(ComputerCreationDTO creationDTO) {
        return videoCardService.findById(creationDTO.getVideoCardId());
    }

    private static Processor getProcessorFromCreationDTO(ComputerCreationDTO creationDTO) {
        return processorService.findById(creationDTO.getProcessorId());
    }
}
