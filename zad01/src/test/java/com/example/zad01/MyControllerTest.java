package com.example.zad01;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

}

