package torimia.superheroes.superhero.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import torimia.superheroes.exceptions.handler.MainExceptionHandler;

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
    protected String toJson(Object object) { //fixme: too open
        return mapper.writeValueAsString(object);
    }

    protected abstract Object getMockMvc();
}
