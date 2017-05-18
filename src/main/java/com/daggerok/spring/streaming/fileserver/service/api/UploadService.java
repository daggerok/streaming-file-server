package com.daggerok.spring.streaming.fileserver.service.api;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UploadService {
    void upload(MultipartFile file, RedirectAttributes redirectAttributes, String owner);
}
