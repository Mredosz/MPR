package com.example.zad01;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyRestService {
    CapybaraRepository repository;

    public MyRestService(CapybaraRepository repository) {
        this.repository = repository;
        this.repository.save(new Capybara("Bob",2));
        this.repository.save(new Capybara("George",5));
        this.repository.save(new Capybara("Dan",12));
    }

    public Capybara getCapybaraByName(String name) {
        return this.repository.findByName(name);
    }
    public ArrayList<Capybara> getAllCapybaras() {
        return (ArrayList<Capybara>) this.repository.findAll();
    }
    public void addCapybara(Capybara capybara) {
        this.repository.save(capybara);
    }

    public void deleteCapybaraByName(String name){
       Capybara capybara = this.repository.findByName(name);
        this.repository.delete(capybara);
    }

    public Capybara updateCapybaraByName(String name, Capybara capybara1){
        Capybara capybara = this.repository.findByName(name);
        capybara.setName(capybara1.getName());
        capybara.setAge(capybara1.getAge());
        this.repository.save(capybara);
        return capybara;
    }
}
