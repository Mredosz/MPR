package com.example.Project.service;

import com.example.Project.Capybara;
import com.example.Project.exeception.exceptionsClass.CapybaraAlreadyExistException;
import com.example.Project.exeception.exceptionsClass.CapybaraNotExistException;
import com.example.Project.repository.CapybaraRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyRestServiceTest {
    @Mock
    private CapybaraRepository repository;
    @InjectMocks
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
    public void findCapybaraByNameWhenCapybaraExist(){
        String name = "Adam";
        Capybara capybara = new Capybara(name, 3);
        when(repository.findByName(name)).thenReturn(capybara);
        var result = service.getCapybaraByName(name);
        assertEquals(result,capybara);
    }
    @Test
    public void findCapybaraByNameWhenCapybaraIsNotExist(){
        when(repository.findByName("Adam")).thenReturn(null);

        assertThrows(CapybaraNotExistException.class, () ->{service.getCapybaraByName("Adam");});
    }

    @Test
    public void addCapybaraWhenCapybaraIsNotExist(){
        String name = "Adam";
        Capybara capybara = new Capybara(name, 3);
        ArgumentCaptor<Capybara> captor = ArgumentCaptor.forClass(Capybara.class);
        when(repository.save(captor.capture())).thenReturn(capybara);

        service.addCapybara(capybara);
        Mockito.verify(repository, Mockito.times(1))
                .save(Mockito.any());
        Capybara result = captor.getValue();
        assertEquals(capybara, result);
    }
    @Test
    public void addCapybaraWhenCapybaraExist(){
        Capybara capybara = new Capybara("Adam", 4);
        when(repository.findByName("Adam")).thenReturn(capybara);

        assertThrows(CapybaraAlreadyExistException.class, () ->{service.addCapybara(capybara);});
    }

    @Test
    public void getAllCapybaraWhenCapybaraExist(){
        String name1 = "Adam";
        String name2 = "Marek";
        var capyList = new ArrayList<Capybara>();
        capyList.add(new Capybara(name1,1));
        capyList.add(new Capybara(name2,5));

        when(repository.findAll()).thenReturn(capyList);

        var result = service.getAllCapybaras();
        assertEquals(capyList, result);
    }
    @Test
    public void getAllCapybaraWhenCapybaraIsNotExist(){
        var capyList = new ArrayList<Capybara>();

        when(repository.findAll()).thenReturn(capyList);

        assertThrows(CapybaraNotExistException.class, () -> {service.getAllCapybaras();});
    }

    @Test
    public void deleteCapybaraByNameWhenCapybaraExist(){
        String name = "Marek";
        Capybara capybara = new Capybara(name,12);

        when(repository.findByName(name)).thenReturn(capybara);

        service.deleteCapybaraByName(name);

        Mockito.verify(repository).delete(capybara);
    }
    @Test
    public void deleteCapybaraByNameWhenCapybaraIsNotExist(){
        String name = "Marek";
        Capybara capybara = new Capybara(name,12);

        when(repository.findByName(name)).thenReturn(capybara);

        service.deleteCapybaraByName(name);

        Mockito.verify(repository).delete(capybara);
    }

    @Test
    public void updateCapybaraByNameWhenCapybaraExist(){
        String name = "Marta";
        Capybara capybara = new Capybara(name,6);
        Capybara updateCapybara = new Capybara(name,12);

        when(repository.findByName(name)).thenReturn(capybara);
        when(repository.findByName(updateCapybara.getName())).thenReturn(updateCapybara);

        Capybara result = service.updateCapybaraByName(name,capybara);


        assertEquals(updateCapybara, result);
    }
    @Test
    public void updateCapybaraByNameWhenCapybaraIsNotExist(){
        String name = "Marta";
        Capybara capybara = new Capybara(name,6);
        Capybara updateCapybara = new Capybara(name,12);

        when(repository.findByName(name)).thenReturn(capybara);
        when(repository.findByName(updateCapybara.getName())).thenReturn(updateCapybara);

        Capybara result = service.updateCapybaraByName(name,capybara);


        assertEquals(updateCapybara, result);
    }
}
