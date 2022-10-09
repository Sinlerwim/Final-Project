package com.FinalProject.config;

import java.util.Base64;

public final class ImageUtil {

    public static String getImgData(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
