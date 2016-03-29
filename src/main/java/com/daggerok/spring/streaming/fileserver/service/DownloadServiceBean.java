package com.daggerok.spring.streaming.fileserver.service;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.domain.FileItemRepository;
import com.daggerok.spring.streaming.fileserver.service.api.DownloadService;
import com.daggerok.spring.streaming.fileserver.service.api.FileService;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class DownloadServiceBean implements DownloadService {
    @Autowired
    FileItemRepository fileItemRepository;

    @Autowired
    FileService fileService;

    @Synchronized
    @PostConstruct
    public void sync() {
        try (Stream<FileItem> items = fileService.getDownloads()) {
            fileItemRepository.save(items.collect(toList()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<FileItem> search(String filename) {
        String like = "%".concat(filename).concat("%");

        return fileItemRepository.findByFilenameLikeIgnoreCase(like);
    }

    @Override
    @Transactional(readOnly = true)
    public void download(Long id, HttpServletResponse response) {
        fileItemRepository.findById(id).ifPresent(fileItem -> fileService.send(fileItem, response));
    }
}
