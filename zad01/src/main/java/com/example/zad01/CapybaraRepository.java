package com.example.zad01;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapybaraRepository extends CrudRepository<Capybara,Long> {
    public Capybara findByName(String name);
}
