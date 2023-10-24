package com.example.zad01;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyRestServiceTest {
    @Mock
    private CapybaraRepository repository;
    private MyRestService service;
    private AutoCloseable openMocks;

    @BeforeEach
    public void init(){
        openMocks = MockitoAnnotations.openMocks(this);
        service = new MyRestService(repository);
    }

    @AfterEach
    public void closeDownTest() throws Exception{
        openMocks.close();
    }

    @Test
    public void checkThatCapybaraIsNotNull(){
        String name = "Adam";
        int age = 3;
        Capybara capybaraTested = new Capybara(name, age);

        Mockito.when(this.repository.findByName(name)).thenReturn(capybaraTested);

        Capybara result = service.getCapybaraByName(name);
        assertEquals(capybaraTested, result);
    }

    @Test
    public void savaCapybara(){
        String name = "Adam";
        int age = 3;
        Capybara capybaraTested = new Capybara(name, age);
        ArgumentCaptor<Capybara> captor = ArgumentCaptor.forClass(Capybara.class);
        Mockito.when(repository.save(captor.capture())).thenReturn(capybaraTested);

        service.addCapybara(capybaraTested);
        Mockito.verify(repository, Mockito.times(1))
                .save(Mockito.any());
        Capybara saveCapy = captor.getValue();
        assertEquals(capybaraTested, saveCapy);

    }
}
