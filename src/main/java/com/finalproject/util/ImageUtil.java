package com.finalproject.util;

import com.finalproject.model.Image;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public final class ImageUtil {

    public static String getImgData(Image image) {
        return Base64.getEncoder().encodeToString(image.getBytes());
    }

    public static String getFirstImage(List<Image> images) {
        return Base64.getEncoder().encodeToString(images.get(0).getBytes());
    }

    public static List<String> getListImgData(List<Image> images) {
        return images.stream()
                .map(image -> Base64.getEncoder().encodeToString(image.getBytes()))
                .collect(Collectors.toList());
    }

    public static byte[] getImgBytesFromString(String imageAsString) {
        return Base64.getDecoder().decode(imageAsString);
    }

    @SneakyThrows
    public static byte[] getImgBytesFromFile(MultipartFile file) {
        return file.getBytes();
    }
}
