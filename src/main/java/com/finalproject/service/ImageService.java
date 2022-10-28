package com.finalproject.service;

import com.finalproject.model.Image;
import com.finalproject.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getImagesByComputerId(String id) {
        return imageRepository.findByComputer_Id(id);
    }

    public void deleteImageById(String id) {
        imageRepository.deleteById(id);
    }
}
