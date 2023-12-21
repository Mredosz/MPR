package com.example.Project.service;

import com.example.Project.Capybara;
import com.example.Project.exeception.exceptionsClass.CapybaraAgeIsToLowException;
import com.example.Project.exeception.exceptionsClass.CapybaraAlreadyExistException;
import com.example.Project.exeception.exceptionsClass.CapybaraNotExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.Project.service.MyRestService.BASE_URL;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyRestServiceTest {
    @Mock
    private RestClient restClient;
    @InjectMocks
    private MyRestService service;
    private AutoCloseable openMocks;

    @BeforeEach
    public void init(){
        openMocks = MockitoAnnotations.openMocks(this);
        restClient = RestClient.create();
        service = new MyRestService();
    }

    @AfterEach
    public void closeDownTest() throws Exception{
        openMocks.close();
    }

    @Test
    public void findCapybaraByNameWhenCapybaraExist(){
        Capybara capybara = new Capybara("Bob", 3);
        when(restClient
                .get()
                .uri(BASE_URL + "/capybara/" + capybara.getName())
                .retrieve()
                .body(Capybara.class)).thenReturn(capybara);
        var result = service.getCapybaraByName(capybara.getName());

        assertEquals(result,capybara);
    }
//    @Test
//    public void findCapybaraByNameWhenCapybaraIsNotExist(){
//        when(repository.findByName("Adam")).thenReturn(empty());
//
//        assertThrows(CapybaraNotExistException.class, () -> service.getCapybaraByName("Adam"));
//    }
//
//    @Test
//    public void addCapybaraWhenCapybaraIsNotExist(){
//        Capybara capybara = new Capybara("Adam", 3);
//
//        when(repository.findByName(capybara.getName())).thenReturn(empty());
//        when(repository.save(capybara)).thenReturn(capybara);
//
//        var result = service.addCapybara(capybara);
//
//        assertEquals(capybara, result.get());
//    }
//    @Test
//    public void addCapybaraWhenCapybaraExist(){
//        Capybara capybara = new Capybara("Adam", 4);
//        when(repository.findByName(capybara.getName())).thenReturn(Optional.of(capybara));
//
//        assertThrows(CapybaraAlreadyExistException.class, () -> service.addCapybara(capybara));
//    }
//
//    @Test
//    public void getAllCapybaraWhenCapybaraExist(){
//        var capyList = new ArrayList<Capybara>();
//        capyList.add(new Capybara("Marek",1));
//        capyList.add(new Capybara("Adam",5));
//
//        when(repository.findAll()).thenReturn(capyList);
//
//        var result = service.getAllCapybaras();
//        assertEquals(capyList, result);
//    }
//    @Test
//    public void getAllCapybaraWhenCapybaraIsNotExist(){
//        var capyList = new ArrayList<Capybara>();
//
//        when(repository.findAll()).thenReturn(capyList);
//
//        assertThrows(CapybaraNotExistException.class, () -> service.getAllCapybaras());
//    }
//
//    @Test
//    public void deleteCapybaraByNameWhenCapybaraExist(){
//        Capybara capybara = new Capybara("Marek",12);
//
//        when(repository.findByName(capybara.getName())).thenReturn(Optional.of(capybara));
//
//        service.deleteCapybaraByName(capybara.getName());
//
//        verify(repository).delete(capybara);
//    }
//    @Test
//    public void deleteCapybaraByNameWhenCapybaraIsNotExist(){
//        Capybara capybara = new Capybara("Adam", 4);
//        when(repository.findByName(capybara.getName())).thenReturn(empty());
//
//        assertThrows(CapybaraNotExistException.class, () -> service.deleteCapybaraByName(capybara.getName()));
//    }
//
//    @Test
//    public void updateCapybaraByNameWhenCapybaraExist(){
//        Capybara capybara = new Capybara("Marta",6);
//        Capybara capybaraUpdate = new Capybara("Marta",12);
//
//        when(repository.findByName(capybara.getName())).thenReturn(Optional.of(capybara));
//        when(repository.save(capybara)).thenReturn(capybaraUpdate);
//
//        var result = service.updateCapybaraByName(capybara.getName(),capybaraUpdate);
//
//
//        assertEquals(capybaraUpdate, result.get());
//    }
//    @Test
//    public void updateCapybaraByNameWhenCapybaraIsNotExist(){
//        Capybara capybara = new Capybara("Adam", 4);
//        when(repository.findByName(capybara.getName())).thenReturn(empty());
//
//        assertThrows(CapybaraNotExistException.class, () -> service.updateCapybaraByName(capybara.getName(),capybara));
//    }
//
//    @Test
//    public void updateCapybaraByNameWhenCapybaraAgeIsToLow(){
//        Capybara capybara = new Capybara("Adam", 4);
//        Capybara capybaraUpdate = new Capybara("Adam", 2);
//        when(repository.findByName(capybara.getName())).thenReturn(Optional.of(capybara));
//
//        assertThrows(CapybaraAgeIsToLowException.class, () -> service.updateCapybaraByName(capybara.getName(),capybaraUpdate));
//    }
//    @Test
//    public void updateCapybaraByNameWhenCapybaraAgeIsOk(){
//        Capybara capybara = new Capybara("Adam", 4);
//        Capybara capybaraUpdate = new Capybara("Adam", 5);
//        when(repository.findByName(capybara.getName())).thenReturn(Optional.of(capybara));
//        when(repository.save(capybara)).thenReturn(capybaraUpdate);
//
//        var result = service.updateCapybaraByName("Adam",capybaraUpdate);
//
//        assertEquals(result.get(), capybaraUpdate);
//    }
//
//    @Test
//    public void findCapybarasThatNameIsContainsNameFromLinkWhenCapybaraIsExist(){
//        var capyList = new ArrayList<Capybara>();
//        var capyListResult = new ArrayList<Capybara>();
//        Capybara capybara1 = new Capybara("Marek",1);
//        Capybara capybara2 = new Capybara("Marek2",5);
//        Capybara capybara3 = new Capybara("Mark",5);
//        capyList.add(capybara1);
//        capyList.add(capybara2);
//        capyList.add(capybara3);
//        capyListResult.add(capybara1);
////        capyListResult.add(capybara2);
//
//        when(repository.findAll()).thenReturn(capyList);
//
//        var result = service.findCapybarasThatNameIsContainsNameFromLink("Marek");
//        assertEquals(capyListResult, result);
//    }
}
