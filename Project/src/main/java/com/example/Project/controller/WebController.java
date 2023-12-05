package com.example.Project.controller;

import com.example.Project.Capybara;
import com.example.Project.service.MyRestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {
    public final MyRestService service;

    public WebController(MyRestService service) {
        this.service = service;
    }
    @GetMapping(value = "/allCapybara")
    public String getViewAll (Model model){
        model.addAttribute("capybaras",service.getAllCapybaras());
        return "capybaras";
    }

    @GetMapping(value = "/addCapybara")
    public String addCapybara(Model model){
        model.addAttribute("capybara", service.addCapybara(new Capybara("",0)));
        return "addCapybara";
    }

    @PostMapping(value = "/addCapybara")
    public String addCapybara(Model model, Capybara capybara){
        service.addCapybara(capybara);
        return "redirect:/allCapybara";
    }
    @GetMapping("/welcome")
    public String getWelcomeView(){
        return "capybaras";
    }
}
