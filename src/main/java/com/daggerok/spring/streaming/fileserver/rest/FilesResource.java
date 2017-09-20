package com.daggerok.spring.streaming.fileserver.rest;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.service.FileService;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class FilesResource {

  final FileService fileService;

  @GetMapping("/api/v1/files")
  public List<FileItem> get() {
    @Cleanup val stream = fileService.getDownloads();
    return stream.collect(toList());
  }
}
