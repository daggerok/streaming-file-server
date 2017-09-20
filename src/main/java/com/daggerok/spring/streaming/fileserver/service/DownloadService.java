package com.daggerok.spring.streaming.fileserver.service;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.domain.FileItemRepository;
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
public class DownloadService {

  final FileService fileService;
  final FileItemRepository fileItemRepository;

  @Synchronized
  @PostConstruct
  @Transactional
  public void sync() {
    @Cleanup val stream = fileService.getDownloads();
    val items = stream.collect(toList());
    fileItemRepository.save(items);
  }

  public List<FileItem> search(final String filename) {
    @Cleanup val stream = fileItemRepository.findByFilenameContainingIgnoreCase(filename);
    return stream.collect(toList());
  }

  public void download(final Long id, final HttpServletResponse response) {

    fileItemRepository.findById(id)
                      .ifPresent(fileItem -> fileService.send(fileItem, response));
  }
}
