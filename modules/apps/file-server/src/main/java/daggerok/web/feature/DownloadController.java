package daggerok.web.feature;

import daggerok.service.DownloadService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static daggerok.web.IndexPage.INDEX;
import static daggerok.web.IndexPage.REDIRECT_INDEX;

@Controller
@RequiredArgsConstructor
@RequestMapping("/download")
public class DownloadController {

  private final DownloadService downloadService;

  @PostMapping
  public String search(@RequestParam("filename") final String filename, final Model model) {
    model.addAttribute("files", downloadService.search(filename));
    return INDEX;
  }

  @GetMapping("/{id}")
  public void download(@PathVariable("id") final Long id, final HttpServletResponse response) {
    downloadService.download(id, response);
  }

  @SneakyThrows
  @GetMapping({ "", "/" })
  public String download() {
    return REDIRECT_INDEX;
  }
}
