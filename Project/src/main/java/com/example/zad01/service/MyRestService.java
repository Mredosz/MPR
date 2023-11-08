package com.example.zad01.service;

import com.example.zad01.Capybara;
import com.example.zad01.exeception.exceptionsClass.CapybaraAgeIsToLowException;
import com.example.zad01.exeception.exceptionsClass.CapybaraAlreadyExistException;
import com.example.zad01.exeception.exceptionsClass.CapybaraNotExistException;
import com.example.zad01.repository.CapybaraRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyRestService {
    CapybaraRepository repository;

    public MyRestService(CapybaraRepository repository) {
        this.repository = repository;
        this.repository.save(new Capybara("Bob", 2));
        this.repository.save(new Capybara("George", 5));
        this.repository.save(new Capybara("Dan", 12));
    }

    public Capybara getCapybaraByName(String name) {
        if (this.repository.findByName(name) != null)
            return this.repository.findByName(name);
        else throw new CapybaraNotExistException();
    }

    public ArrayList<Capybara> getAllCapybaras() {
        if (!((ArrayList<Capybara>) this.repository.findAll()).isEmpty())
            return (ArrayList<Capybara>) this.repository.findAll();
        else throw new CapybaraNotExistException();
    }

    public void addCapybara(Capybara capybara) {
        if (repository.findByName(capybara.getName()) == null)
            this.repository.save(capybara);
        else
            throw new CapybaraAlreadyExistException();
    }

    public void deleteCapybaraByName(String name) {
        Capybara capybara = this.repository.findByName(name);
        if (capybara != null)
            this.repository.delete(capybara);
        else
            throw new CapybaraNotExistException();
    }

    public Capybara updateCapybaraByName(String name, Capybara capybara1) {
        Capybara capybara = this.repository.findByName(name);
        if (capybara != null) {
            if (capybara.getAge() < capybara1.getAge())
            capybara.setAge(capybara1.getAge());
            else throw new CapybaraAgeIsToLowException();
            return this.repository.save(capybara);
        } else {
            throw new CapybaraNotExistException();
        }
    }
}
