package com.daggerok.spring.streaming.fileserver.web;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.domain.FileItemRepository;
import com.daggerok.spring.streaming.fileserver.web.annotation.Get;
import com.daggerok.spring.streaming.fileserver.web.annotation.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@WebPage("/")
public class IndexPage {

    public static final String INDEX = "index";

    public static final String REDIRECT_INDEX = "redirect:/";

    @Autowired
    FileItemRepository fileItemRepository;

    @Get
    public String index(Model model) {
        List<FileItem> files = fileItemRepository.findAll();

        model.addAttribute("files", files);
        return INDEX;
    }
}
