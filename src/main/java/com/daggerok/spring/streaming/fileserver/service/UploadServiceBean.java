package com.daggerok.spring.streaming.fileserver.service;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.domain.FileItemRepository;
import com.daggerok.spring.streaming.fileserver.service.api.FileService;
import com.daggerok.spring.streaming.fileserver.service.api.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class UploadServiceBean implements UploadService {

    @Autowired
    FileService fileService;

    @Autowired
    FileItemRepository fileItemRepository;

    @PostConstruct
    @Transactional(readOnly = true)
    public void sync() {
        fileService.setupUploads();
    }

    @Override
    public void upload(MultipartFile file, RedirectAttributes redirectAttributes) {
        FileItem fileItem = fileService.receive(file);

        fileItemRepository.save(fileItem);
        redirectAttributes.addFlashAttribute("message",
                String.format("done with %s.", file.getOriginalFilename()));
    }
}
