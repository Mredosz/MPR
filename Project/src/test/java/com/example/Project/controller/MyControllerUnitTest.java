package com.example.Project.controller;

import com.example.Project.Capybara;
import com.example.Project.exeception.CapybaraExceptionHandler;
import com.example.Project.exeception.exceptionsClass.CapybaraAgeIsToLowException;
import com.example.Project.exeception.exceptionsClass.CapybaraAlreadyExistException;
import com.example.Project.exeception.exceptionsClass.CapybaraNotExistException;
import com.example.Project.service.MyRestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MyControllerUnitTest {
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
        when(service.getCapybaraByName("Bob")).thenReturn(Optional.of(new Capybara("Bob", 2)));

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
        var capybara = new Capybara("Bob", 2);

        when(service.addCapybara(any())).thenReturn(Optional.of(capybara));

        mockMvc.perform(post("/capybara/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Bob\", \"age\": \"2\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addCapybaraWhenCapybaraExist() throws Exception {
        when(service.addCapybara(any())).thenThrow(new CapybaraAlreadyExistException());

        mockMvc.perform(post("/capybara/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Bob\", \"age\": \"2\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllCapybaraWhenCapybaraExist() throws Exception {
        var capyList = new ArrayList<Capybara>();
        capyList.add(new Capybara("Maciek", 12));
        capyList.add(new Capybara("Monika", 7));

        when(service.getAllCapybaras()).thenReturn(capyList);

        mockMvc.perform(get("/capybaras"))
                .andExpect(jsonPath("$.[0].age").value("12"))
                .andExpect(jsonPath("$.[0].name").value("Maciek"))
                .andExpect(jsonPath("$.[1].age").value("7"))
                .andExpect(jsonPath("$.[1].name").value("Monika"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCapybaraWhenCapybaraIsNotExist() throws Exception {

        when(service.getAllCapybaras()).thenThrow(new CapybaraNotExistException());

        mockMvc.perform(get("/capybaras"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCapybaraByNameWhenCapybaraExist() throws Exception {
        mockMvc.perform(delete("/capybara/delete/Maciek"))
                .andExpect(status().isOk());

        verify(service).deleteCapybaraByName("Maciek");
    }

    @Test
    public void deleteCapybaraByNameWhenCapybaraIsNotExist() throws Exception {
        doThrow(CapybaraNotExistException.class).when(service).deleteCapybaraByName(any());

        mockMvc.perform(delete("/capybara/delete/Maciek"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCapybaraByNameWhenCapybaraExist() throws Exception {
        var capybara = new Capybara("Marcel", 4);
        when(service.updateCapybaraByName(eq(capybara.getName()), any())).thenReturn(Optional.of(capybara));

        mockMvc.perform(put("/capybara/update/Marcel")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Marcel\", \"age\": \"5\"}")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void updateCapybaraByNameWhenCapybaraIsNotExist() throws Exception {
        var capybara = new Capybara("Marcel", 4);
        when(service.updateCapybaraByName(eq(capybara.getName()), any())).thenThrow(new CapybaraNotExistException());

        mockMvc.perform(put("/capybara/update/Marcel")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"age\": \"5\"}")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void updateCapybaraByNameWhenCapybaraAgeIsToLow() throws Exception {
        var capybara = new Capybara("Marcel", 4);
        when(service.updateCapybaraByName(eq(capybara.getName()), any()))
                .thenThrow(new CapybaraAgeIsToLowException());

        mockMvc.perform(put("/capybara/update/Marcel")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Marcel\", \"age\": \"2\"}")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}

