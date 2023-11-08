package com.example.zad01.controller;

import com.example.zad01.Capybara;
import com.example.zad01.service.MyRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class MyController {

    private final MyRestService myRestService;

    @Autowired
    public MyController(MyRestService myRestService){
        this.myRestService = myRestService;
    }

    @GetMapping("/capybara/{name}")
    public Capybara getByName(@PathVariable("name") String name){
           return this.myRestService.getCapybaraByName(name);
    }

    @GetMapping("/capybaras")
    public ArrayList<Capybara> getAll(){
        return this.myRestService.getAllCapybaras();
    }

    @PostMapping("/capybara/add")
    public void postCapybara(@RequestBody Capybara capybara){
        this.myRestService.addCapybara(capybara);
    }

    @DeleteMapping("/capybara/delete/{name}")
    public void deleteByName(@PathVariable("name")String name){this.myRestService.deleteCapybaraByName(name);}

    @PutMapping("/capybara/update/{name}")
    public Capybara updateByName(@PathVariable("name")String name, @RequestBody Capybara capybara){
        return this.myRestService.updateCapybaraByName(name, capybara);
    }
}
