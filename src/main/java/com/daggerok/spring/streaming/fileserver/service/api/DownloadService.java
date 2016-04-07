package com.daggerok.spring.streaming.fileserver.service.api;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

public interface DownloadService {

    Stream<FileItem> search(String path);

    void download(Long id, HttpServletResponse response);
}
