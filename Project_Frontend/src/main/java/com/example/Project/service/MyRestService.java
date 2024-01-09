package com.example.Project.service;

import com.example.Project.Capybara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyRestService {
    public static final String BASE_URL = "http://localhost:8080";

    @Autowired
    RestClient restClient;

    public MyRestService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Capybara getCapybaraByName(String name) {
        return restClient
                .get()
                .uri(BASE_URL + "/capybara/" + name)
                .retrieve()
                .body(Capybara.class);
    }

    public ArrayList<Capybara> getAllCapybaras() {
        return restClient
                .get()
                .uri(BASE_URL + "/capybaras")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public void addCapybara(Capybara capybara) {
        ResponseEntity<Void> response = restClient
                .post()
                .uri(BASE_URL + "/capybara/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(capybara)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteCapybaraByName(String name) {
        ResponseEntity<Void> response = restClient
                .delete()
                .uri(BASE_URL + "/capybara/delete/" + name)
                .retrieve()
                .toBodilessEntity();
    }

    public Capybara updateCapybaraByName(String name, Capybara capybara) {
        return restClient
                .put()
                .uri(BASE_URL + "/capybara/update/" + name)
                .body(capybara)
                .retrieve()
                .body(Capybara.class);
    }

    public List<Capybara> findCapybarasThatNameIsContainsNameFromLink(String name) {
        return restClient
                .get()
                .uri(BASE_URL + "/capybaras/" + name)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Capybara>>() {});
    }
}
