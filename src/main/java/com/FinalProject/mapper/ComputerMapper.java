package com.FinalProject.mapper;

import com.FinalProject.dto.ComputerDTO;
import com.FinalProject.model.Computer;

public final class ComputerMapper {

    private ComputerMapper() {};
    public static ComputerDTO toDTO(Computer computer) {
        final ComputerDTO computerDTO = new ComputerDTO();
        computerDTO.setId(computer.getId());
        computerDTO.setImage(computer.getImage());
        computerDTO.setManufacturer(computer.getManufacturer());
        computerDTO.setModel(computer.getModel());
        computerDTO.setRam(computer.getRam());
        computerDTO.setRamType(computer.getRamType());
        computerDTO.setOperatingSystem(computer.getOperatingSystem());
        computerDTO.setPrice(computer.getPrice());
        return computerDTO;
    }

    public static Computer fromDTO(ComputerDTO computerDTO) {
        final Computer computer = new Computer();
        computer.setId(computerDTO.getId());
        computer.setManufacturer(computerDTO.getManufacturer());
        computer.setImage(computerDTO.getImage());
        computer.setModel(computerDTO.getModel());
        computer.setRam(computerDTO.getRam());
        computer.setRamType(computerDTO.getRamType());
        computer.setOperatingSystem(computerDTO.getOperatingSystem());
        computer.setPrice(computerDTO.getPrice());
        return computer;
    }
}
