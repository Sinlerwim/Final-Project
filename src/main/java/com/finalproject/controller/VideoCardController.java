package com.finalproject.controller;

import com.finalproject.dto.VideoCardCreationDTO;
import com.finalproject.mapper.VideoCardMapper;
import com.finalproject.model.VideoCard;
import com.finalproject.service.VideoCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/video-cards")
@Controller
public class VideoCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoCardController.class);

    private final VideoCardService videoCardService;


    public VideoCardController(VideoCardService videoCardService) {
        this.videoCardService = videoCardService;
    }


    @GetMapping("/create")
    public ModelAndView getVideoCardCreation(ModelAndView modelAndView) {
        VideoCardCreationDTO videoCardCreationDTO = new VideoCardCreationDTO();
        modelAndView.addObject("videoCard", videoCardCreationDTO);
        modelAndView.addObject("videoCards", videoCardService.findAll());
        modelAndView.setViewName("createVideoCard");
        return modelAndView;
    }


    @PostMapping("/create")
    public ModelAndView createVideoCard(@ModelAttribute @Valid VideoCardCreationDTO videoCardCreationDTO,
                                        ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("videoCard", videoCardCreationDTO);
            modelAndView.setViewName("createVideoCard");
            return modelAndView;
        } else {
            VideoCard videoCard = VideoCardMapper.fromVideoCardCreationDTO(videoCardCreationDTO);
            videoCardService.save(videoCard);
            LOGGER.info("Created video card: " + videoCard);
            return new ModelAndView("redirect:/video-cards/create");
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView getVideoCardUpdate(@PathVariable("id") String id, ModelAndView modelAndView) {
        final VideoCard videoCard = videoCardService.findById(id);
        modelAndView.addObject("videoCard", videoCard);
        modelAndView.addObject("videoCards", videoCardService.findAll());
        modelAndView.setViewName("updateVideoCard");
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateVideoCard(@ModelAttribute @Valid VideoCard videoCard, ModelAndView modelAndView,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("videoCard", videoCard);
            modelAndView.setViewName("updateVideoCard");
            return modelAndView;
        } else {
            videoCardService.save(videoCard);
            LOGGER.info("Updated video card: " + videoCard);
            return new ModelAndView("redirect:/video-cards/create");
        }
    }

    @PostMapping("/video-card/{id}")
    public ModelAndView deleteVideoCard(@PathVariable("id") String id) {
        videoCardService.delete(id);
        LOGGER.info("Deleted video card: " + id);
        return new ModelAndView("redirect:/processors/create");
    }
}
