package torimia.superheroes.superhero.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import torimia.superheroes.superhero.model.dto.IdRequest;
import torimia.superheroes.superhero.model.dto.SuperheroDto;
import torimia.superheroes.superhero.service.SuperheroServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static torimia.superheroes.superhero.controller.SuperheroesController.Path.*;

@ExtendWith(MockitoExtension.class)
class SuperheroesControllerTest extends ControllerConfigForTests {

    @Mock
    private SuperheroServiceImpl service;

    @InjectMocks
    private SuperheroesController controller;

    private final String ID = "/1";

    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @Override
    protected Object getMockMvc() {
        return controller;
    }

    @Test
    void createWithFirstNameNull() throws Exception {
        SuperheroDto dto = createSuperheroDto();
        dto.setFirstName("");

        MockHttpServletResponse response = createSuperhero(dto, BASE_URL);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createWithLastNameNull() throws Exception {
        SuperheroDto dto = createSuperheroDto();
        dto.setLastName("");

        MockHttpServletResponse response = createSuperhero(dto, BASE_URL);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createWithAgeLessThan1() throws Exception {
        SuperheroDto dto = createSuperheroDto();
        dto.setAge(0);

        MockHttpServletResponse response = createSuperhero(dto, BASE_URL);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void updateSuccess() throws Exception {
        SuperheroDto dto = createSuperheroDto();
        dto.setAge(30);

        MockHttpServletResponse response = updateSuperhero(dto, BASE_URL + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void updateWithNFirstNameNull() throws Exception {
        SuperheroDto dto = createSuperheroDto();
        dto.setFirstName("");

        MockHttpServletResponse response = updateSuperhero(dto, BASE_URL + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void updateWithLastNameNull() throws Exception {
        SuperheroDto dto = createSuperheroDto();
        dto.setLastName("");

        MockHttpServletResponse response = updateSuperhero(dto, BASE_URL + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void updateWithAgeLessThan1() throws Exception {
        SuperheroDto dto = createSuperheroDto();
        dto.setAge(0);

        MockHttpServletResponse response = updateSuperhero(dto, BASE_URL + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addFriend() throws Exception {
        IdRequest idRequest = IdRequest.of(2L);

        MockHttpServletResponse response = addItemList(idRequest, BASE_URL + ADD_FRIEND + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void addFriendWithIdNull() throws Exception {
        IdRequest idRequest = IdRequest.of(null);

        MockHttpServletResponse response = addItemList(idRequest, BASE_URL + ADD_FRIEND + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deleteFriend() throws Exception {
        IdRequest idRequest = IdRequest.of(2L);

        MockHttpServletResponse response = deleteItemList(idRequest, BASE_URL + DELETE_FRIEND + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deleteFriendWithIdLessThan0() throws Exception {
        IdRequest idRequest = IdRequest.of(-3L);

        MockHttpServletResponse response = deleteItemList(idRequest, BASE_URL + DELETE_FRIEND + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addEnemy() throws Exception {
        IdRequest idRequest = IdRequest.of(2L);

        MockHttpServletResponse response = addItemList(idRequest, BASE_URL + ADD_ENEMY + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void addEnemyWithIdNull() throws Exception {
        IdRequest idRequest = IdRequest.of(null);

        MockHttpServletResponse response = addItemList(idRequest, BASE_URL + ADD_ENEMY + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deleteEnemy() throws Exception {
        IdRequest idRequest = IdRequest.of(2L);

        MockHttpServletResponse response = deleteItemList(idRequest, BASE_URL + DELETE_ENEMY + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deleteEnemyWithIdLessThan0() throws Exception {
        IdRequest idRequest = IdRequest.of(-3L);

        MockHttpServletResponse response = deleteItemList(idRequest, BASE_URL + DELETE_ENEMY + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addAward() throws Exception {
        IdRequest idRequest = IdRequest.of(2L);

        MockHttpServletResponse response = addItemList(idRequest, BASE_URL + ADD_AWARD + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void addAwardWithIdNull() throws Exception {
        IdRequest idRequest = IdRequest.of(null);

        MockHttpServletResponse response = addItemList(idRequest, BASE_URL + ADD_AWARD + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deleteAward() throws Exception {
        IdRequest idRequest = IdRequest.of(2L);

        MockHttpServletResponse response = deleteItemList(idRequest, BASE_URL + DELETE_AWARD + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deleteAwardWithIdLessThan0() throws Exception {
        IdRequest idRequest = IdRequest.of(-3L);

        MockHttpServletResponse response = deleteItemList(idRequest, BASE_URL + DELETE_AWARD + ID);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private SuperheroDto createSuperheroDto() {
        Long SUPERHERO_ID = 56L;
        String SUPERHERO_NICKNAME = "Toliia";
        String SUPERHERO_FIRST_NAME = "Andrew";
        String SUPERHERO_LAST_NAME = "Semenuk";
        int SUPERHERO_AGE = 25;
        String SUPERHERO_SUPER_POWER = "No super power";
        int SUPERHERO_DAMAGE = 10;
        int SUPERHERO_HEALTH = 100;

        return SuperheroDto.builder()
                .id(SUPERHERO_ID)
                .nickname(SUPERHERO_NICKNAME)
                .firstName(SUPERHERO_FIRST_NAME)
                .lastName(SUPERHERO_LAST_NAME)
                .age(SUPERHERO_AGE)
                .superPower(SUPERHERO_SUPER_POWER)
                .health(SUPERHERO_HEALTH)
                .damage(SUPERHERO_DAMAGE)
                .build();
    }

    private MockHttpServletResponse createSuperhero(SuperheroDto dto, String url) throws Exception {
        return mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(dto)))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse updateSuperhero(SuperheroDto dto, String url) throws Exception {
        return mockMvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(dto)))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse addItemList(IdRequest idRequest, String url) throws Exception {
        return mockMvc.perform(
                patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(idRequest)))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse deleteItemList(IdRequest idRequest, String url) throws Exception {
        return mockMvc.perform(
                delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(idRequest)))
                .andReturn().getResponse();
    }
}