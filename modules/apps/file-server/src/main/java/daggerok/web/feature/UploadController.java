package daggerok.web.feature;

import daggerok.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static daggerok.service.util.SecurityUtil.anonymousFriendlyName;
import static daggerok.service.util.SecurityUtil.displayName;
import static daggerok.web.IndexPage.REDIRECT_INDEX;

@Controller
@RequiredArgsConstructor
public class UploadController {

  final UploadService uploadService;

  @PostMapping("/upload")
  public String post(@RequestParam("file") final MultipartFile file, final RedirectAttributes redirectAttributes) {

    val owner = displayName().getOrDefault(displayName, anonymousFriendlyName);
    uploadService.upload(file, redirectAttributes, owner);
    return REDIRECT_INDEX;
  }
}
