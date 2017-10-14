package daggerok.rest;

import daggerok.domain.FileItem;
import daggerok.domain.FileItemRepository;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DownloadRestResource {

  final FileItemRepository fileItemRepository;

  @Synchronized
  @Transactional
  public void sync(final List<FileItem> items) {
    fileItemRepository.save(items);
  }

  public List<FileItem> search(final String filename) {
    @Cleanup val stream = fileItemRepository.findByFilenameContainingIgnoreCase(filename);
    return stream.collect(toList());
  }

  public FileItem download(final Long id) {

    return fileItemRepository.findById(id)
                             .orElseThrow(() -> new RuntimeException("implement me...."));
  }
}
