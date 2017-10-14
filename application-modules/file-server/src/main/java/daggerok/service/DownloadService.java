package daggerok.service;

import daggerok.client.model.FileItem;
import daggerok.client.FileItemRestClient;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DownloadService {

  final FileService fileService;
  final FileItemRestClient fileItemRestClient;

  @Synchronized
  @PostConstruct
  public void sync() {
    @Cleanup val stream = fileService.getDownloads();
    val items = stream.collect(toList());
    fileItemRestClient.save(items);
  }

  public List<FileItem> search(final String filename) {
    return fileItemRestClient.findByFilenameContainingIgnoreCase(filename);
  }

  public void download(final Long id, final HttpServletResponse response) {

    fileItemRestClient.findById(id)
                      .ifPresent(fileItem -> fileService.send(fileItem, response));
  }
}
