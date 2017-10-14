package daggerok.web;

import daggerok.client.FileItemRestClient;
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

  final FileItemRestClient fileItemRestClient;

  @GetMapping("")
  public String redirect(final Model model) {
    return REDIRECT_INDEX;
  }

  @GetMapping("/")
  public String index(final Model model) {
    val fileItems = fileItemRestClient.findAll();
    model.addAttribute("files", fileItems);
    return INDEX;
  }
}
