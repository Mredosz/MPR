package com.example.Project.controller;

import com.example.Project.Capybara;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyControllerIntegrityTest {
    private static final String URI = "http://localhost:8080";
    @Test
    public void findCapybaraByNameWhenCapybaraExist(){
        when()
                .get(URI + "/capybara/Dan")
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(3))
                .body("name", equalTo("Dan"))
                .body("age", equalTo(12))
                .log()
                .body();
    }
    @Test
    public void findCapybaraByNameWhenCapybaraIsNotExist(){
        when()
                .get(URI + "/capybara/fsfds")
                .then()
                .statusCode(404)
                .assertThat()
                .log()
                .body();
    }

    @Test
    public void findAllCapybara(){
       List<Capybara> list = List.of(when()
                .get(URI + "/capybaras")
                .then()
                .statusCode(200)
                .extract()
               .as(Capybara[].class));

       assertEquals(2,list.get(0).getAge());
       assertEquals("Bob",list.get(0).getName());
       assertEquals(5,list.get(1).getAge());
       assertEquals("George",list.get(1).getName());
       assertEquals(12,list.get(2).getAge());
       assertEquals("Dan",list.get(2).getName());
    }

    @Test
    public void addCapybaraWhenCapybaraIsNotExist(){
        with()
                .body(new Capybara("Kate", 3))
                .contentType("application/json")
                .post(URI + "/capybara/add")
                .then()
                .statusCode(200)
                .body("name", equalTo("Kate"))
                .body("age", equalTo(3))
                .log()
                .body();
    }
    @Test
    public void addCapybaraWhenCapybaraExist(){
        with()
                .body(new Capybara("George", 3))
                .contentType("application/json")
                .post(URI + "/capybara/add")
                .then()
                .statusCode(400)
                .log()
                .body();
    }

    @Test
    public void deleteCapybaraWhenCapybaraExist(){
        with()
                .delete(URI + "/capybara/delete/DanBob")
                .then()
                .statusCode(200)
                .log()
                .body();
    }
    @Test
    public void deleteCapybaraWhenCapybaraIsNotExist(){
        with()
                .delete(URI + "/capybara/delete/gfdsgfs")
                .then()
                .statusCode(404)
                .log()
                .body();
    }

    @Test
    public void updateCapybaraWhenCapybaraExist(){
        with()
                .body(new Capybara("Kate", 4))
                .contentType("application/json")
                .put(URI + "/capybara/update/Kate")
                .then()
                .statusCode(200)
                .body("name", equalTo("Kate"))
                .body("age", equalTo(4))
                .log()
                .body();
    }
    @Test
    public void updateCapybaraWhenCapybaraIsNotExist(){
        with()
                .body(new Capybara("fdsfds", 4))
                .contentType("application/json")
                .put(URI + "/capybara/update/fdsfds")
                .then()
                .statusCode(404)
                .log()
                .body();
    }
}
