package com.finalproject.controller;

import com.finalproject.service.ImageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/images")
@Controller
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/delete/{computerId}/{imageId}")
    public ModelAndView deleteImage(@PathVariable("computerId") String computerId, @PathVariable("imageId") String imageId) {
        imageService.deleteImageById(imageId);
        return new ModelAndView("redirect:/update/computer/" + computerId);
    }
}
