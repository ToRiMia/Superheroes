package torimia.superheroes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import torimia.superheroes.exceptions.handler.MainExceptionHandler;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.services.AwardServiceImpl;
import torimia.superheroes.services.SuperheroServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static torimia.superheroes.controllers.SuperheroesController.Path.BASE_URL;

@ExtendWith(MockitoExtension.class)
class AwardsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AwardServiceImpl service;

    @InjectMocks
    private AwardsController controller;

    private JacksonTester<SuperheroDto> jsonSuperhero;
    private JacksonTester<IdRequest> jsonId;

    private final String ID = "/1";

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new MainExceptionHandler())
                .build();
    }

//    @Test
//    void createSuccess() throws Exception {
//        SuperheroDto dto = createSuperheroDto();
//
//        MockHttpServletResponse response = createSuperhero(dto, BASE_URL);
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}