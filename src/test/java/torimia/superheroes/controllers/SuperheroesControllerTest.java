package torimia.superheroes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import torimia.superheroes.exceptions.handler.MainExceptionHandler;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.services.SuperheroServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static torimia.superheroes.controllers.SuperheroesController.Path.BASE_URL;

@ExtendWith(MockitoExtension.class)
class SuperheroesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SuperheroServiceImpl service;

    @InjectMocks
    private SuperheroesController controller;

    private JacksonTester<SuperheroDto> jsonSuperhero;

    private final Long SUPERHERO_ID = 56L;
    private final String SUPERHERO_NICKNAME = "Toliia";
    private final String SUPERHERO_FIRST_NAME = "Andrew";
    private final String SUPERHERO_LAST_NAME = "Semenuk";
    private final int SUPERHERO_AGE = 25;
    private final String SUPERHERO_SUPER_POWER = "No super power";

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new MainExceptionHandler())
                .build();

    }

    @Test
    void createSuccess() throws Exception {
        SuperheroDto dto = SuperheroDto.builder()
                .id(SUPERHERO_ID)
                .nickname(SUPERHERO_NICKNAME)
                .firstName(SUPERHERO_FIRST_NAME)
                .lastName(SUPERHERO_LAST_NAME)
                .age(SUPERHERO_AGE)
                .superPower(SUPERHERO_SUPER_POWER)
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSuperhero.write(dto).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Disabled
    @Test
    void createExpectedValidationException() throws Exception {
        SuperheroDto dto = SuperheroDto.builder()
                .id(SUPERHERO_ID)
                .nickname("")
                .firstName(SUPERHERO_FIRST_NAME)
                .lastName(SUPERHERO_LAST_NAME)
                .age(SUPERHERO_AGE)
                .superPower(SUPERHERO_SUPER_POWER)
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSuperhero.write(dto).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}