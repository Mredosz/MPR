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
    public void findAllCapybaraWhenCapybaraExist() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/capybaras"))
                .andRespond(MockRestResponseCreators
                        .withSuccess(""" 
                                [{"name":"Dan", "age":12}]
                                """, MediaType.APPLICATION_JSON));
        var list = service.getAllCapybaras();
        assertEquals("Dan", list.get(0).getName());
        assertEquals(12, list.get(0).getAge());
    }
}
