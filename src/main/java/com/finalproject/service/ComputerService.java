package com.finalproject.service;

import com.finalproject.dto.ComputerDTO;
import com.finalproject.mapper.ComputerMapper;
import com.finalproject.model.*;
import com.finalproject.repository.ComputerRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ComputerService {

    private final ComputerRepository computerRepository;

    private final ProcessorService processorService;
    private final VideoCardService videoCardService;
    private final DiskDriveService diskDriveService;

    @Autowired
    public ComputerService(ComputerRepository computerRepository, ProcessorService processorService,
                           VideoCardService videoCardService, DiskDriveService diskDriveService) {
        this.computerRepository = computerRepository;
        this.processorService = processorService;
        this.videoCardService = videoCardService;
        this.diskDriveService = diskDriveService;
    }

    public ComputerDTO findByIdAndReturnDTO(String id) {
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

    public Page<Computer> findPageOfNotHidden(int numberOfPage) {
        return computerRepository.findByIsHiddenFalse(PageRequest.of(numberOfPage, 9));
    }

    public Page<Computer> findByOperatingSystem(String operatingSystem, int numberOfPage) {
        return computerRepository.findByCriteria(operatingSystem, PageRequest.of(numberOfPage, 9));
    }

    public Iterable<String> getAllIds() {
        return StreamSupport.stream(computerRepository.findAll().spliterator(), false)
                .map(Computer::getId)
                .collect(Collectors.toList());
    }

    public ComputerDTO findByModel(String model) {
        if (model.trim().length() == 0) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        return computerRepository.findByModel(model).map(ComputerMapper::toDTO).orElse(null);
    }

    @SneakyThrows
    @Transactional
    public String createDefaultComputer() {
        final Random random = new Random();
        final Computer computer = new Computer();
        final Processor processor = new Processor();
        processor.setModel("Default-model");
        processor.setFrequency("3.7 GHz");
        processor.setManufacturer(ProcessorManufacturer.Intel);
        computer.setProcessor(processorService.saveOrReturnIfExist(processor));
        final VideoCard videoCard = new VideoCard();
        videoCard.setManufacturer("Nvidia");
        videoCard.setModel("GeForce GTX 1060");
        videoCard.setType(VideoCardType.DISCRETE);
        videoCard.setVram("6144 Mb");
        computer.setVideoCard(videoCardService.saveOrReturnIfExist(videoCard));
        final DiskDrive diskDrive = new DiskDrive();
        diskDrive.setName("WD40");
        diskDrive.setType(DiskDriveType.HDD);
        diskDrive.setCapacity("2TB");
        computer.setDiskDrive(diskDriveService.saveOrReturnIfExist(diskDrive));
        computer.setManufacturer("Manufacturer-" + random.nextInt(1, 100));
        computer.setModel("Default-model");
        computer.setRam("4096 Mb");
        computer.setRamType("DDR4");
        computer.setOperatingSystem("Windows 10");
        computer.setPrice(random.nextInt(5000,20000));
        computer.setDescription("Default PC");
        byte[] img1Bytes = FileUtils.readFileToByteArray(new File("src/main/resources/static/images/computer.jpg"));
        byte[] img2Bytes = FileUtils.readFileToByteArray(new File("src/main/resources/static/images/smartphone.jpg"));
        Image img1 = new Image();
        Image img2 = new Image();
        img1.setBytes(img1Bytes);
        img2.setBytes(img2Bytes);
//        String encodedImg = Base64.getEncoder().encodeToString(img);
//        System.out.println(encodedImg);
//        System.out.println(encodedImg.length());
        computer.setImages(List.of(img1, img2));
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
        if (computerRepository.existsById(id)) {
            computerRepository.deleteById(id);
        }
    }

    public Iterable<Computer> getRandomN(int limit) {
        return computerRepository.getRandomN(limit);
    }

    public String save(Computer computer) {
        return computerRepository.save(computer).getId();
    }

    public Computer findById(String id) {
        return computerRepository.findById(id).get();
    }

    public void hideById(String id) {
        computerRepository.hideComputerById(id);
    }
}
