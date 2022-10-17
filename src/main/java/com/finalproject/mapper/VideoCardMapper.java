package com.finalproject.mapper;

import com.finalproject.dto.VideoCardCreationDTO;
import com.finalproject.model.VideoCard;

public class VideoCardMapper {

    public static VideoCard fromVideoCardCreationDTO(VideoCardCreationDTO videoCardCreationDTO) {
        final VideoCard videoCard = new VideoCard();
        videoCard.setManufacturer(videoCardCreationDTO.getManufacturer());
        videoCard.setModel(videoCardCreationDTO.getModel());
        videoCard.setType(videoCardCreationDTO.getType());
        videoCard.setVram(videoCardCreationDTO.getVram());
        return videoCard;
    }
}
