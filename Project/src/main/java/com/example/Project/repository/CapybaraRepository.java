package com.example.Project.repository;

import com.example.Project.Capybara;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapybaraRepository extends CrudRepository<Capybara,Long> {
    public Optional<Capybara> findByName(String name);
}
