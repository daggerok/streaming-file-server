package com.daggerok.spring.streaming.fileserver.service.contract;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DownloadService {

    List<FileItem> search(String path);

    void download(Long id, HttpServletResponse response);
}
