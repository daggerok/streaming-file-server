package com.daggerok.spring.streaming.fileserver.service;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.domain.FileItemRepository;
import com.daggerok.spring.streaming.fileserver.service.api.DownloadService;
import com.daggerok.spring.streaming.fileserver.service.api.FileService;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DownloadServiceBean implements DownloadService {

    final FileItemRepository fileItemRepository;
    final FileService fileService;

    @Synchronized
    @PostConstruct
    @Transactional
    public void sync() {

        @Cleanup val stream = fileService.getDownloads();
        val items = stream.collect(toList());

        fileItemRepository.save(items);
    }

    @Override
    public List<FileItem> search(String filename) {

        val like = "%" + filename + "%";
        @Cleanup val stream = fileItemRepository.findByFilenameLikeIgnoreCase(like);

        return stream.collect(toList());
    }

    @Override
    public void download(Long id, HttpServletResponse response) {

        fileItemRepository.findById(id)
                          .ifPresent(fileItem -> fileService.send(fileItem, response));
    }
}
