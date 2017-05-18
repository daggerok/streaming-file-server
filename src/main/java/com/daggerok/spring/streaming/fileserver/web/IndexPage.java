package com.daggerok.spring.streaming.fileserver.web;

import com.daggerok.spring.streaming.fileserver.domain.FileItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexPage {

    public static final String INDEX = "index";
    public static final String REDIRECT_INDEX = "redirect:/";

    final FileItemRepository fileItemRepository;

    @GetMapping("")
    public String redirect(Model model) {
        return REDIRECT_INDEX;
    }

    @GetMapping("/")
    public String index(Model model) {

        val files = fileItemRepository.findAll();

        model.addAttribute("files", files);
        return INDEX;
    }
}
