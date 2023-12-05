package com.example.Project.service;

import com.example.Project.Capybara;
import com.example.Project.exeception.exceptionsClass.CapybaraAgeIsToLowException;
import com.example.Project.exeception.exceptionsClass.CapybaraAlreadyExistException;
import com.example.Project.exeception.exceptionsClass.CapybaraNotExistException;
import com.example.Project.repository.CapybaraRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyRestService {
    CapybaraRepository repository;

    public MyRestService(CapybaraRepository repository) {
        this.repository = repository;
        this.repository.save(new Capybara("Bob", 2));
        this.repository.save(new Capybara("George", 5));
        this.repository.save(new Capybara("Dan", 12));
        this.repository.save(new Capybara("DanMark", 6));
        this.repository.save(new Capybara("DanBob", 7));
    }

    public Optional<Capybara> getCapybaraByName(String name) {
        if (this.repository.findByName(name).isPresent())
            return this.repository.findByName(name);
        else throw new CapybaraNotExistException();
    }

    public ArrayList<Capybara> getAllCapybaras() {
        if (!((ArrayList<Capybara>) this.repository.findAll()).isEmpty())
            return (ArrayList<Capybara>) this.repository.findAll();
        else throw new CapybaraNotExistException();
    }

    public Optional<Capybara> addCapybara(Capybara capybara) {
        if (repository.findByName(capybara.getName()).isEmpty())
            return Optional.of(this.repository.save(capybara));
        else
            throw new CapybaraAlreadyExistException();
    }

    public void deleteCapybaraByName(String name) {
        var capybara = this.repository.findByName(name);
        if (capybara.isPresent())
            this.repository.delete(capybara.get());
        else
            throw new CapybaraNotExistException();
    }

    public Optional<Capybara> updateCapybaraByName(String name, Capybara capybara1) {
        var capybara = this.repository.findByName(name);
        if (capybara.isPresent()) {
            if (capybara.get().getAge() < capybara1.getAge())
                capybara.get().setAge(capybara1.getAge());
            else throw new CapybaraAgeIsToLowException();
            return Optional.of(this.repository.save(capybara.get()));
        } else {
            throw new CapybaraNotExistException();
        }
    }

    public List<Capybara> findCapybarasThatNameIsContainsNameFromLink(String name) {
        var capybaraListWithAllCapybara = ((ArrayList<Capybara>) this.repository.findAll());
        return capybaraListWithAllCapybara.stream()
                .filter(capybara -> capybara.getName().contains(name))
                .toList();
    }
}
