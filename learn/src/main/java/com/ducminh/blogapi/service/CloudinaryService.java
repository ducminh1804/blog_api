package com.ducminh.blogapi.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public Map uploadImage(MultipartFile file) throws IOException {
        Map param = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );
        return cloudinary.uploader().upload(file.getBytes(), param);
    }

    public Map uploadVideo(MultipartFile file) throws IOException {
        Map param = ObjectUtils.asMap(
                "resource_type", "video",
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );
        return cloudinary.uploader().upload(file.getBytes(), param);
    }
}
