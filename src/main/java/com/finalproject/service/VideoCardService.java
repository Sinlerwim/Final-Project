package com.finalproject.service;

import com.finalproject.model.VideoCard;
import com.finalproject.repository.VideoCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoCardService {

    private final VideoCardRepository videoCardRepository;

    @Autowired
    public VideoCardService(VideoCardRepository videoCardRepository) {
        this.videoCardRepository = videoCardRepository;
    }

    public VideoCard saveOrReturnIfExist(VideoCard videoCard) {
        if(videoCardRepository.existsByManufacturerAndModelAndTypeAndVramAllIgnoreCase(videoCard.getManufacturer(),
                videoCard.getModel(), videoCard.getType(), videoCard.getVram())) {
            System.out.println("Processor exist. Returning...");
            return videoCardRepository.findFirstByManufacturerAndModelAndTypeAndVramAllIgnoreCase(videoCard.getManufacturer(),
                    videoCard.getModel(), videoCard.getType(), videoCard.getVram());
        } else {
            return videoCardRepository.save(videoCard);
        }
    }

    public Iterable<VideoCard> findAll() {
        return videoCardRepository.findAll();
    }

    public VideoCard findById(String id) {
        if(id.isEmpty()){
            return null;
        }
        return videoCardRepository.findById(id).get();
    }

    public String save(VideoCard videoCard) {
        return videoCardRepository.save(videoCard).getId();
    }

    public void delete(String id) {
        if(videoCardRepository.existsById(id)) {
            videoCardRepository.deleteById(id);
        }
    }
}
