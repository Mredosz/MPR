package com.example.Project.controller;

import com.example.Project.Capybara;
import com.example.Project.service.MyRestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    public final MyRestService service;

    public WebController(MyRestService service) {
        this.service = service;
    }
    @GetMapping("/index")
    public String getWelcomeView(){
        return "index";
    }
    @GetMapping(value = "/allCapybara")
    public String getViewAll (Model model){
        model.addAttribute("allCapybaras",service.getAllCapybaras());
        return "allCapybara";
    }

    @GetMapping(value = "/addCapybara")
    public String addCapybara(Model model){
        model.addAttribute("capybara", new Capybara("",0));
        model.addAttribute("allCapybaras", service.getAllCapybaras());
        return "addCapybara";
    }

    @PostMapping(value = "/addCapybara")
    public String addCapybara(@ModelAttribute Capybara capybara){
        if (capybara.getAge()<=0){
            return "addCapybara";
        }
            service.addCapybara(capybara);
            return "redirect:/allCapybara";
    }

    @GetMapping(value = "/updateCapybara/{name}")
    public String updateCapybara(Model model, @PathVariable("name") String name){
        var capybara = service.getCapybaraByName(name);
        model.addAttribute("capybara",service.updateCapybaraByName(name,capybara));
        model.addAttribute("allCapybaras",service.getAllCapybaras());
        return "updateCapybara";
    }

    @PostMapping(value = "/updateCapybara")
    public String updateCapybara(@ModelAttribute Capybara capybara){
        service.updateCapybaraByName(capybara.getName(),capybara);
        return "redirect:/allCapybara";
    }

    @GetMapping(value = "/deleteCapybara/{name}")
    public String deleteCapybara(@PathVariable("name") String name){
        service.deleteCapybaraByName(name);
        return "redirect:/allCapybara";
    }
}
