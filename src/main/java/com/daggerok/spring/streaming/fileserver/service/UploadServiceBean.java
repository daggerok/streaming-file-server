package com.daggerok.spring.streaming.fileserver.service;

import com.daggerok.spring.streaming.fileserver.domain.FileItemRepository;
import com.daggerok.spring.streaming.fileserver.service.contract.FileService;
import com.daggerok.spring.streaming.fileserver.service.contract.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadServiceBean implements UploadService {

  final FileService fileService;
  final FileItemRepository fileItemRepository;

  @PostConstruct
  @Transactional
  public void sync() {
    fileService.setupUploads();
  }

  @Override
  @Transactional
  public void upload(final MultipartFile file,
                     final RedirectAttributes redirectAttributes,
                     final String owner) {

    val fileItem = fileService.receive(file)
                              .setOwner(owner);

    fileItemRepository.save(fileItem);
    redirectAttributes.addFlashAttribute("message",
                                         format("done with %s.", file.getOriginalFilename()));
  }
}
