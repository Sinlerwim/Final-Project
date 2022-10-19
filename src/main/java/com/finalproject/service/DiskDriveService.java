package com.finalproject.service;


import com.finalproject.model.DiskDrive;
import com.finalproject.repository.DiskDriveRepository;
import org.springframework.stereotype.Service;

@Service
public class DiskDriveService {

    private final DiskDriveRepository diskDriveRepository;

    public DiskDriveService(DiskDriveRepository diskDriveRepository) {
        this.diskDriveRepository = diskDriveRepository;
    }

    public DiskDrive saveOrReturnIfExist(DiskDrive diskDrive) {
        if (diskDriveRepository.existsByNameAndTypeAndCapacityAllIgnoreCase(diskDrive.getName(), diskDrive.getType(),
                diskDrive.getCapacity())) {
            System.out.println("Processor exist. Returning...");
            return diskDriveRepository.findFirstByNameAndTypeAndCapacityAllIgnoreCase(diskDrive.getName(), diskDrive.getType(),
                    diskDrive.getCapacity());
        } else {
            return diskDriveRepository.save(diskDrive);
        }
    }

    public Iterable<DiskDrive> findAll() {
        return diskDriveRepository.findAll();
    }

    public DiskDrive findById(String id) {
        if(id.isEmpty()){
            return null;
        }
        return diskDriveRepository.findById(id).get();
    }

    public String save(DiskDrive diskDrive) {
        return diskDriveRepository.save(diskDrive).getId();
    }

    public void delete(String id) {
        if(diskDriveRepository.existsById(id)) {
            diskDriveRepository.deleteById(id);
        }
    }
}
