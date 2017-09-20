package com.daggerok.spring.streaming.fileserver.web.feature;

import com.daggerok.spring.streaming.fileserver.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.daggerok.spring.streaming.fileserver.web.IndexPage.INDEX;

@Controller
@RequiredArgsConstructor
@RequestMapping("/download")
public class DownloadController {

  final DownloadService downloadService;

  @PostMapping
  public String search(@RequestParam("filename") final String filename, final Model model) {
    model.addAttribute("files", downloadService.search(filename));
    return INDEX;
  }

  @GetMapping("/{id}")
  public void download(@PathVariable("id") final Long id, final HttpServletResponse response) {
    downloadService.download(id, response);
  }
}
