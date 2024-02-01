package com.example.Project.service;

import com.example.Project.Capybara;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.example.Project.service.MyRestService.BASE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RestClientTest
public class MyRestServiceUnitTest {
    @InjectMocks
    private MyRestService service;
    private MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    private RestClient.Builder builder = RestClient.builder();

    @BeforeEach
    public void setUp() {
        customizer.customize(builder);
        service = new MyRestService(builder.build());
    }

    @Test
    public void findCapybaraByNameWhenCapybaraExist() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybara/Dan"))
                .andRespond(MockRestResponseCreators
                        .withSuccess(""" 
                                {"name":"Dan", "age":12}
                                """, MediaType.APPLICATION_JSON));
        Capybara capybara = service.getCapybaraByName("Dan");
        assertEquals("Dan", capybara.getName());
    }
    @Test
    public void findCapybaraByNameWhenCapybaraIsNotExist() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybara/Dan"))
                .andRespond(MockRestResponseCreators
                        .withBadRequest());
        assertThrows(RuntimeException.class, () -> service.getCapybaraByName("Dan"));
    }
    @Test
    public void findAllCapybaraWhenCapybaraExist() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybaras"))
                .andRespond(MockRestResponseCreators
                        .withSuccess(""" 
                                [{"name":"Dan", "age":12},
                                {"name":"Bob", "age":4},
                                {"name":"Clara", "age":7}]
                                """, MediaType.APPLICATION_JSON));
        var list = service.getAllCapybaras();
        assertEquals("Dan", list.get(0).getName());
        assertEquals(12, list.get(0).getAge());
        assertEquals("Bob", list.get(1).getName());
        assertEquals(4, list.get(1).getAge());
        assertEquals("Clara", list.get(2).getName());
        assertEquals(7, list.get(2).getAge());
    }
    @Test
    public void findAllCapybaraWhenCapybaraIsNotExist() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybaras"))
                .andRespond(MockRestResponseCreators
                        .withSuccess(""" 
                                []
                                """, MediaType.APPLICATION_JSON));
        var list = service.getAllCapybaras();
        assertEquals(0, list.size());
    }

    @Test
    public void addCapybaraWhenCapybaraIsNotExist(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybara/add"))
                .andRespond(MockRestResponseCreators
                        .withSuccess());
        service.addCapybara(new Capybara("Maciek", 12));
    }

    @Test
    public void addCapybaraWhenCapybaraExist(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybara/add"))
                .andRespond(MockRestResponseCreators
                        .withBadRequest());
        assertThrows(RuntimeException.class, () -> service.addCapybara(new Capybara("Maciek", 12)));
    }

    @Test
    public void deleteCapybaraWhenCapybaraExist(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybara/delete/Maciek"))
                .andRespond(MockRestResponseCreators
                        .withSuccess());
    }

    @Test
    public void deleteCapybaraWhenCapybaraIsNotExist(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybara/delete/Maciek"))
                .andRespond(MockRestResponseCreators
                        .withBadRequest());
    }

    @Test
    public void updateCapybaraWhenCapybaraExist(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybara/update/Maciek"))
                .andRespond(MockRestResponseCreators
                        .withSuccess(""" 
                                {"name":"Maciek", "age":12}
                                """, MediaType.APPLICATION_JSON));
        Capybara capybara = service.updateCapybaraByName("Maciek", new Capybara("Maciek", 12));
        assertEquals("Maciek", capybara.getName());
        assertEquals(12, capybara.getAge());
    }

    @Test
    public void updateCapybaraWhenCapybaraIsNotExist(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybara/update/Maciek"))
                .andRespond(MockRestResponseCreators
                        .withBadRequest());
        assertThrows(RuntimeException.class,
                () -> service.updateCapybaraByName("Maciek", new Capybara("Maciek", 12)));
    }

}
