package com.ducminh.blogapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostFileRequest {
    private String title;
    private MultipartFile file;
    private Set<String> tags;
    private String kind;
}
