package daggerok.rest;

import daggerok.domain.FileItem;
import daggerok.domain.FileItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadRestResource {

  final FileItemRepository fileItemRepository;

  @Transactional
  public void upload(final FileItem fileItem) {
    fileItemRepository.save(fileItem);
  }
}
