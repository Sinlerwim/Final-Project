package com.FinalProject.service;

import com.FinalProject.dto.ComputerDTO;
import com.FinalProject.mapper.ComputerMapper;
import com.FinalProject.model.*;
import com.FinalProject.repository.ComputerRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ComputerService {

    private final ComputerRepository computerRepository;

    @Autowired
    public ComputerService(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    public ComputerDTO findById(String id) {
        return computerRepository.findById(id).map(ComputerMapper::toDTO).orElse(null);
    }

    public Iterable<ComputerDTO> findAll() {
        return StreamSupport.stream(computerRepository.findAll().spliterator(), false)
                .map(ComputerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<Computer> findPage(int numberOfPage) {
        return computerRepository.findAll(PageRequest.of(numberOfPage, 9));
    }

    public Iterable<String> getAllIds() {
        return StreamSupport.stream(computerRepository.findAll().spliterator(), false)
                .map(Computer::getId)
                .collect(Collectors.toList());
    }
    public ComputerDTO findByModel(String model) {
        if(model.trim().length() == 0) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        return computerRepository.findByModel(model).map(ComputerMapper::toDTO).orElse(null);
    }

    @SneakyThrows
    @Transactional
    public String createDefaultComputer() {
        final Random random = new Random();
        final Processor processor = new Processor();
        processor.setName("Default-name");
        processor.setFrequency("3.7 GHz");
        processor.setManufacturer(ProcessorManufacturer.Intel);
        final VideoCard videoCard = new VideoCard();
        videoCard.setName("Nvidia GeForce GTX 1060");
        videoCard.setType(VideoCardType.DISCRETE);
        videoCard.setVRam(6144);
        final DiskDrive diskDrive = new DiskDrive();
        diskDrive.setName("WD40");
        diskDrive.setType(DiskDriveType.HDD);
        diskDrive.setCapacity("2TB");
        final Computer computer = new Computer();
        computer.setManufacturer("Manufacturer-" + random.nextInt(1,100));
        computer.setModel("Default-model");
        computer.setRam("4096 Mb");
        computer.setRamType("DDR4");
        computer.setProcessor(processor);
        computer.setVideoCard(videoCard);
        computer.setDiskDrive(diskDrive);
        computer.setOperatingSystem("Windows 10");
        computer.setColor("Black");
        computer.setMaterial("Steel");
        computer.setPrice(20000);
        computer.setDescription("Default PC");
        byte[] img = FileUtils.readFileToByteArray(new File("src/main/resources/static/images/computer.jpg"));
//        String encodedImg = Base64.getEncoder().encodeToString(img);
//        System.out.println(encodedImg);
//        System.out.println(encodedImg.length());
        computer.setImage(img);
        return computerRepository.save(computer).getId();
    }

    public String create(ComputerDTO computerDTO) {
        final Computer computer = ComputerMapper.fromDTO(computerDTO);
        return computerRepository.save(computer).getId();
    }

    public ComputerDTO update(String id, ComputerDTO computerDTO) {
        final Computer computer = computerRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        computer.setManufacturer(computerDTO.getManufacturer());
        computer.setModel(computerDTO.getModel());
        computer.setRam(computerDTO.getRam());
        computer.setRamType(computerDTO.getRamType());
        computer.setOperatingSystem(computerDTO.getOperatingSystem());
        computer.setPrice(computerDTO.getPrice());
        final Computer updated = computerRepository.save(computer);
        return ComputerMapper.toDTO(updated);
    }

    public void delete(String id) {
        if(computerRepository.existsById(id)) {
            computerRepository.deleteById(id);
        }
    }

    public Iterable<Computer> getRandomN(int limit) {
        return computerRepository.getRandomN(limit);
    }
}
