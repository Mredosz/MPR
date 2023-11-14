package com.example.Project.controller;

import com.example.Project.Capybara;
import com.example.Project.exeception.CapybaraExceptionHandler;
import com.example.Project.exeception.exceptionsClass.CapybaraAlreadyExistException;
import com.example.Project.exeception.exceptionsClass.CapybaraNotExistException;
import com.example.Project.service.MyRestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MyControllerTest {
    @Mock
    private MyRestService service;
    @InjectMocks
    private MyController controller;
    private MockMvc mockMvc;
    private AutoCloseable openMocks;

    @BeforeEach
    public void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new MyController(service);
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new CapybaraExceptionHandler(), controller
        ).build();
    }

    @AfterEach
    public void closeDownTest() throws Exception {
        openMocks.close();
    }

    @Test
    public void searchCapybaraByNameWhenCapybaraExist() throws Exception {
        when(service.getCapybaraByName("Bob")).thenReturn(new Capybara("Bob", 2));

        mockMvc.perform(get("/capybara/Bob"))
                .andExpect(jsonPath("$.age").value("2"))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchCapybaraByNameWhenCapybaraIsNotExist() throws Exception {
        when(service.getCapybaraByName("Ala")).thenThrow(new CapybaraNotExistException());

        mockMvc.perform(get("/capybara/Ala"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addCapybaraWhenCapybaraIsNotExist() throws Exception {
        Capybara capybara = new Capybara("Bob", 2);

        when(service.addCapybara(any())).thenThrow(new CapybaraAlreadyExistException());

        mockMvc.perform(post("/capybara/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Bob\", \"age\": \"2\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void adCapybaraWhenCapybaraExist() {

    }

    @Test
    public void getAllCapybaraWhenCapybaraExist() {
        var capyList = new ArrayList<Capybara>();
        capyList.add(new Capybara("Maciek", 12));
        capyList.add(new Capybara("Monika", 7));

        when(service.getAllCapybaras()).thenReturn(capyList);

        var result = controller.getAll();
        assertEquals(capyList, result);
    }

    @Test
    public void getAllCapybaraWhenCapybaraIsNotExist() {

    }

    @Test
    public void deleteCapybaraByNameWhenCapybaraExist() {
        String name = "Marcel";
        Capybara capybara = new Capybara(name, 6);

        controller.deleteByName(name);

        Mockito.verify(service, Mockito.times(1))
                .deleteCapybaraByName(name);
    }

    @Test
    public void deleteCapybaraByNameWhenCapybaraIsNotExist() {

    }

    @Test
    public void updateCapybaraByNameWhenCapybaraExist() {
        String name = "Marcel";
        Capybara capybara = new Capybara(name, 4);
        Capybara updateCapybara = new Capybara(name, 7);

        when(service.updateCapybaraByName(name, capybara)).thenReturn(capybara);

        Capybara result = controller.updateByName(name, capybara);

        Mockito.verify(service).updateCapybaraByName(name, capybara);

        assertEquals(capybara, result);
    }

    @Test
    public void updateCapybaraByNameWhenCapybaraIsNotExist() {

    }
}

