package com.finalproject.repository;

import com.finalproject.model.VideoCard;
import com.finalproject.model.VideoCardType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Repository
public interface VideoCardRepository extends CrudRepository<VideoCard,String> {
    boolean existsByManufacturerAndModelAndTypeAndVramAllIgnoreCase(String manufacturer, String model, VideoCardType type, String vRam);

//    boolean existsByManufacturerAndModelAndTypeAndVRamAllIgnoreCase(@NotEmpty String manufacturer, @NotEmpty String model, @NotNull VideoCardType type, String VRam);

    VideoCard findFirstByManufacturerAndModelAndTypeAndVramAllIgnoreCase(@NotEmpty String manufacturer, @NotEmpty String model, @NotNull VideoCardType type, String VRam);


}
