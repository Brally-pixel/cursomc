package com.jorgesoares.cursomc.services;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public interface ImageService {

    BufferedImage getJpgImageFromFile(MultipartFile uploadedFile);

    BufferedImage pngToJpg(BufferedImage img);

    InputStream getInputStream(BufferedImage img, String extension);

    BufferedImage cropSquare(BufferedImage sourceImg);

    BufferedImage resize(BufferedImage sourceImg, int size);
}
