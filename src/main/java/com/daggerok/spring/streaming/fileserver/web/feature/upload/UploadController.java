package com.daggerok.spring.streaming.fileserver.web.feature.upload;

import com.daggerok.spring.streaming.fileserver.service.api.UploadService;
import com.daggerok.spring.streaming.fileserver.web.annotation.Post;
import com.daggerok.spring.streaming.fileserver.web.annotation.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static com.daggerok.spring.streaming.fileserver.web.IndexPage.REDIRECT_INDEX;

@Controller
@WebPage("/upload")
public class UploadController {

    @Autowired
    UploadService uploadService;

    @Post
    public String post(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        final String filename = file.getOriginalFilename();

        if (null != filename && !"".equals(filename)) {
            uploadService.upload(file, redirectAttributes);
        }
        return REDIRECT_INDEX;
    }
}
