package com.daggerok.spring.streaming.fileserver.web.feature.upload;

import com.daggerok.spring.streaming.fileserver.service.api.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.daggerok.spring.streaming.fileserver.web.IndexPage.REDIRECT_INDEX;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UploadResource {

    final UploadService uploadService;
    final ConnectionRepository connectionRepository;

    @PostMapping("/upload")
    public String post(@RequestParam("file") MultipartFile file,
                       RedirectAttributes redirectAttributes) {

        val connection = connectionRepository.findPrimaryConnection(Facebook.class);

        uploadService.upload(file, redirectAttributes, connection.getDisplayName());
        return REDIRECT_INDEX;
    }
}
