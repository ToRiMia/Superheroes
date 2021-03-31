package torimia.superheroes.superhero.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.org.apache.http.auth.BasicUserPrincipal;
import torimia.superheroes.exceptions.handler.MainExceptionHandler;

import java.security.Principal;

@ExtendWith(MockitoExtension.class)
public abstract class ControllerConfigForTests {

    protected static MockMvc mockMvc;

    protected ObjectMapper mapper = new ObjectMapper();

    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(getMockMvc())
                .setControllerAdvice(new MainExceptionHandler())
                .build();
    }

    @SneakyThrows
    protected String toJson(Object object) {
        return mapper.writeValueAsString(object);
    }

    protected abstract Object getMockMvc();
}
