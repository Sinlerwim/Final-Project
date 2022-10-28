package com.finalproject.mapper;

import com.finalproject.dto.ComputerCreationDTO;
import com.finalproject.dto.ComputerDTO;
import com.finalproject.dto.ComputerUpdateDTO;
import com.finalproject.model.*;
import com.finalproject.service.DiskDriveService;
import com.finalproject.service.ImageService;
import com.finalproject.service.ProcessorService;
import com.finalproject.service.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public final class ComputerMapper {

    @Autowired
    private ComputerMapper(ProcessorService processorService, VideoCardService videoCardService, DiskDriveService diskDriveService, ImageService imageService) {
        ComputerMapper.processorService = processorService;
        ComputerMapper.videoCardService = videoCardService;
        ComputerMapper.diskDriveService = diskDriveService;
        ComputerMapper.imageService = imageService;
    }

    private static ProcessorService processorService;

    private static VideoCardService videoCardService;

    private static DiskDriveService diskDriveService;

    private static ImageService imageService;

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

        final Processor processor = computer.getProcessor();
        if (processor != null) {
            computerDTO.setProcessorManufacturer(processor.getManufacturer());
            computerDTO.setProcessorModel(processor.getModel());
            computerDTO.setProcessorFrequency(processor.getFrequency());
        }

        final VideoCard videoCard = computer.getVideoCard();
        if (videoCard != null) {
            computerDTO.setVideoCardManufacturer(videoCard.getManufacturer());
            computerDTO.setVideoCardModel(videoCard.getModel());
            computerDTO.setVideoCardType(videoCard.getType());
            computerDTO.setVram(videoCard.getVram());
        }

        final DiskDrive diskDrive = computer.getDiskDrive();
        if (diskDrive != null) {
            computerDTO.setDiskDriveName(diskDrive.getName());
            computerDTO.setDiskDriveType(diskDrive.getType());
            computerDTO.setDiskDriveCapacity(diskDrive.getCapacity());
        }

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

    public static ComputerUpdateDTO toComputerUpdateDTO(Computer computer) {
        final ComputerUpdateDTO computerUpdateDTO = new ComputerUpdateDTO();
        computerUpdateDTO.setId(computer.getId());
        computerUpdateDTO.setImages(computer.getImages());
        computerUpdateDTO.setManufacturer(computer.getManufacturer());
        computerUpdateDTO.setModel(computer.getModel());
        computerUpdateDTO.setRam(computer.getRam());
        computerUpdateDTO.setRamType(computer.getRamType());
        computerUpdateDTO.setOperatingSystem(computer.getOperatingSystem());
        computerUpdateDTO.setDescription(computer.getDescription());
        computerUpdateDTO.setPrice(computer.getPrice());

        if (computer.getProcessor() != null) {
            computerUpdateDTO.setProcessorId(computer.getProcessor().getId());
        }

        if (computer.getVideoCard() != null) {
            computerUpdateDTO.setVideoCardId(computer.getVideoCard().getId());
        }

        if (computer.getDiskDrive() != null) {
            computerUpdateDTO.setDiskDriveId(computer.getDiskDrive().getId());
        }

        return computerUpdateDTO;
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

        computer.setProcessor(getProcessorById(creationDTO.getProcessorId()));
        computer.setVideoCard(getVideoCardById(creationDTO.getVideoCardId()));
        computer.setDiskDrive(getDiskDriveById(creationDTO.getDiskDriveId()));
        return computer;
    }

    private static DiskDrive getDiskDriveById(String id) {
        return diskDriveService.findById(id);
    }

    private static VideoCard getVideoCardById(String id) {
        return videoCardService.findById(id);
    }

    private static Processor getProcessorById(String id) {
        return processorService.findById(id);
    }

    public static Computer fromComputerUpdateDTO(ComputerUpdateDTO updateDTO) {
        Computer computer = new Computer();
        computer.setId(updateDTO.getId());
        computer.setManufacturer(updateDTO.getManufacturer());
        computer.setModel(updateDTO.getModel());
        computer.setRam(updateDTO.getRam());
        computer.setRamType(updateDTO.getRamType());
        computer.setOperatingSystem(updateDTO.getOperatingSystem());
        computer.setPrice(updateDTO.getPrice());
        computer.setDescription(updateDTO.getDescription());

        List<Image> Images = imageService.getImagesByComputerId(updateDTO.getId());
        List<Image> newImages = updateDTO.getNewImages().stream().map(multipartFile -> {
                    try {
                        if (!multipartFile.isEmpty()) {
                            Image image = new Image();
                            image.setBytes(multipartFile.getBytes());
                            return image;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
        Images.addAll(newImages);
        computer.setImages(Images);
        computer.setProcessor(getProcessorById(updateDTO.getProcessorId()));
        computer.setVideoCard(getVideoCardById(updateDTO.getVideoCardId()));
        computer.setDiskDrive(getDiskDriveById(updateDTO.getDiskDriveId()));
        return computer;
    }
}
