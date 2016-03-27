package com.daggerok.spring.streaming.fileserver.web.feature.download;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.service.api.DownloadService;
import com.daggerok.spring.streaming.fileserver.web.annotation.Get;
import com.daggerok.spring.streaming.fileserver.web.annotation.Post;
import com.daggerok.spring.streaming.fileserver.web.annotation.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.daggerok.spring.streaming.fileserver.web.IndexPage.INDEX;

@Controller
@WebPage("/download")
public class DownloadController {

    @Autowired
    DownloadService downloadService;

    @Post
    public String search(@RequestParam("path") String path, Model model) {
        List<FileItem> files = downloadService.search(path);

        if (!files.isEmpty()) {
            model.addAttribute("files", files);
        }
        return INDEX;
    }

    @Get(value = "/{id}")
    public void download(@PathVariable("id") String id, HttpServletResponse response) {
        downloadService.download(id, response);
    }
}
