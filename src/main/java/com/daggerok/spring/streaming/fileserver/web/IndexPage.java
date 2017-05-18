package com.daggerok.spring.streaming.fileserver.web;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.domain.FileItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class IndexPage {

    public static final String INDEX = "index";
    public static final String REDIRECT_INDEX = "redirect:/";

    final FileItemRepository fileItemRepository;

    @GetMapping
    public String index(Model model) {

        val files = fileItemRepository.findAll();

        model.addAttribute("files", files);
        return INDEX;
    }

    @ResponseBody
    @GetMapping("/test")
    public List<FileItem> get() {
        return fileItemRepository.findAll();
    }
}
