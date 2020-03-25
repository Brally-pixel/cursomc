package com.jorgesoares.cursomc.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jorgesoares.cursomc.services.S3Service;
import com.jorgesoares.cursomc.services.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3ServiceImpl implements S3Service {


    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        InputStream is = null;
        try {
            is = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
        String contentType = multipartFile.getContentType();
        return uploadFile(is, fileName, contentType);
    }

    @Override
    public URI uploadFile(InputStream is, String fileName, String contentType) {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            LOG.info("Iniciando upload");
            amazonS3.putObject(bucketName, fileName, is, meta);
            LOG.info("Upload finalizado");

            return amazonS3.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI" + e.getMessage());
        }
    }
}
