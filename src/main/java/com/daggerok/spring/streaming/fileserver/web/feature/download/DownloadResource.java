package com.daggerok.spring.streaming.fileserver.web.feature.download;

import com.daggerok.spring.streaming.fileserver.service.api.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.daggerok.spring.streaming.fileserver.web.IndexPage.INDEX;

@Controller
@RequiredArgsConstructor
@RequestMapping("/download")
public class DownloadResource {

    final DownloadService downloadService;

    @PostMapping
    public String search(@RequestParam("filename") String filename, Model model) {

        model.addAttribute("files", downloadService.search(filename));
        return INDEX;
    }

    @GetMapping("/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {
        downloadService.download(id, response);
    }
}
