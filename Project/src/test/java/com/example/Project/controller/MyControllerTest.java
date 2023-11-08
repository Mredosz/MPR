package com.example.Project.controller;

import com.example.Project.Capybara;
import com.example.Project.service.MyRestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyControllerTest {
    @Mock
    private MyRestService service;
    private MyController controller;
    private AutoCloseable openMocks;

    @BeforeEach
    public void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new MyController(service);
    }

    @AfterEach
    public void closeDownTest() throws Exception {
        openMocks.close();
    }

    @Test
    public void searchCapybaraByName(){
        String name = "Bob";
        Capybara capybaraTested = new Capybara(name,2);
        Mockito.when(this.service.getCapybaraByName(name)).thenReturn(capybaraTested);

        Capybara result = controller.getByName(name);
        assertEquals(capybaraTested, result);
    }

    @Test
    public void postCapybara(){
        String name = "Marcin";
        Capybara capybara = new Capybara(name,4);

        controller.postCapybara(capybara);

        Mockito.verify(service).addCapybara(capybara);
    }

    @Test
    public void getAll(){
        var capyList = new ArrayList<Capybara>();
        capyList.add(new Capybara("Maciek", 12));
        capyList.add(new Capybara("Monika", 7));

        Mockito.when(service.getAllCapybaras()).thenReturn(capyList);

        var result = controller.getAll();
        assertEquals(capyList, result);
    }

    @Test
    public void deleteByName(){
        String name = "Marcel";
        Capybara capybara = new Capybara(name,6);

        controller.deleteByName(name);

        Mockito.verify(service, Mockito.times(1))
                .deleteCapybaraByName(name);
    }

    @Test
    public void updateByName(){
        String name = "Marcel";
        Capybara capybara = new Capybara(name,4);
        Capybara updateCapybara = new Capybara(name,7);

        Mockito.when(service.updateCapybaraByName(name,capybara)).thenReturn(capybara);

        Capybara result = controller.updateByName(name,capybara);

        Mockito.verify(service).updateCapybaraByName(name,capybara);

        assertEquals(capybara, result);
    }
}

