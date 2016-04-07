package com.daggerok.spring.streaming.fileserver.web.feature.download;

import com.daggerok.spring.streaming.fileserver.service.api.DownloadService;
import com.daggerok.spring.streaming.fileserver.web.annotation.Get;
import com.daggerok.spring.streaming.fileserver.web.annotation.Post;
import com.daggerok.spring.streaming.fileserver.web.annotation.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import static com.daggerok.spring.streaming.fileserver.web.IndexPage.INDEX;
import static java.util.stream.Collectors.toList;

@WebPage("/download")
public class DownloadResource {

    @Autowired
    DownloadService downloadService;

    @Post
    public String search(@RequestParam("filename") String filename, Model model) {

        model.addAttribute("files", downloadService.search(filename).collect(toList()));

        return INDEX;
    }

    @Get("/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {

        downloadService.download(id, response);
    }
}
