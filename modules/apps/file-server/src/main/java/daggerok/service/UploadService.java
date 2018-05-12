package daggerok.service;

import daggerok.client.FileItemRestClient;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UploadService {

  final FileService fileService;
  final FileItemRestClient fileItemRestClient;

  @PostConstruct
  public void sync() {
    fileService.setupUploads();
  }

  public void upload(final MultipartFile file,
                     final RedirectAttributes redirectAttributes,
                     final String owner) {

    val fileItem = fileService.receive(file)
                              .setOwner(owner);

    val res = fileItemRestClient.save(fileItem);
    redirectAttributes.addFlashAttribute("message",
                                         format("done with %s.", file.getOriginalFilename()));
  }
}
