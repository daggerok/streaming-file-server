package com.daggerok.spring.streaming.fileserver.service.api;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

public interface FileService {

    Stream<FileItem> getDownloads();

    void send(FileItem fileItem, HttpServletResponse response);

    void setupUploads();

    FileItem receive(MultipartFile file);
}
