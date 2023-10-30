package com.example.zad01;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

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
        Capybara capybara = new Capybara(name, age);

        Mockito.when(this.repository.findByName(name)).thenReturn(capybara);

        Capybara result = service.getCapybaraByName(name);
        assertEquals(capybara, result);
    }

    @Test
    public void savaCapybara(){
        String name = "Adam";
        int age = 3;
        Capybara capybara = new Capybara(name, age);
        ArgumentCaptor<Capybara> captor = ArgumentCaptor.forClass(Capybara.class);
        Mockito.when(repository.save(captor.capture())).thenReturn(capybara);

        service.addCapybara(capybara);
        Mockito.verify(repository, Mockito.times(1))
                .save(Mockito.any());
        Capybara result = captor.getValue();
        assertEquals(capybara, result);
    }

    @Test
    public void getAllCapybara(){
        String name1 = "Adam";
        String name2 = "Marek";
        var capyList = new ArrayList<Capybara>();
        capyList.add(new Capybara(name1,1));
        capyList.add(new Capybara(name2,5));

        Mockito.when(repository.findAll()).thenReturn(capyList);

        var result = service.getAllCapybaras();
        assertEquals(capyList, result);
    }

    @Test
    public void deleteCapybaraByName(){
        String name = "Marek";
        Capybara capybara = new Capybara(name,12);

        Mockito.when(repository.findByName(name)).thenReturn(capybara);

        service.deleteCapybaraByName(name);

        Mockito.verify(repository).delete(capybara);
    }

    @Test
    public void updateCapybaraByName(){
        String name = "Marta";
        Capybara capybara = new Capybara(name,6);
        Capybara updateCapybara = new Capybara(name,12);

        Mockito.when(repository.findByName(name)).thenReturn(capybara);
        Mockito.when(repository.findByName(updateCapybara.getName())).thenReturn(updateCapybara);

        Capybara result = service.updateCapybaraByName(name,capybara);


        assertEquals(updateCapybara, result);
    }
}
