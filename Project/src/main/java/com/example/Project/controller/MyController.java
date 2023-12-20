package com.example.Project.controller;

import com.example.Project.Capybara;
import com.example.Project.service.MyRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class MyController {

    private final MyRestService myRestService;

    @Autowired
    public MyController(MyRestService myRestService){
        this.myRestService = myRestService;
    }

    @GetMapping("/capybara/{name}")
    public Optional<Capybara> getByName(@PathVariable("name") String name){
           return this.myRestService.getCapybaraByName(name);
    }

    @GetMapping("/capybaras")
    public ArrayList<Capybara> getAll(){
        return this.myRestService.getAllCapybaras();
    }

    @PostMapping("/capybara/add")
    public Optional<Capybara> postCapybara(@RequestBody Capybara capybara){
       return this.myRestService.addCapybara(capybara);
    }

    @DeleteMapping("/capybara/delete/{name}")
    public void deleteByName(@PathVariable("name")String name){
        this.myRestService.deleteCapybaraByName(name);
    }

    @PutMapping("/capybara/update/{name}")
    public Optional<Capybara> updateByName(@PathVariable("name")String name, @RequestBody Capybara capybara){
        return this.myRestService.updateCapybaraByName(name, capybara);
    }

    @GetMapping("/capybaras/{name}")
    public List<Capybara> getAllCapybaraThatContainsName(@PathVariable("name") String name){
        return this.myRestService.findCapybarasThatNameIsContainsNameFromLink(name);
    }
}
