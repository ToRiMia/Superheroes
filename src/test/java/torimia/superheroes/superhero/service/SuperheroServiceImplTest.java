package torimia.superheroes.superhero.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.model.dto.SuperheroDto;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.award.AwardRepository;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.service.SuperheroServiceImpl;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static torimia.superheroes.TestingUtils.createListOf;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceImplTest {

    @InjectMocks
    private SuperheroServiceImpl service;
    @Mock
    private SuperheroRepository repository;
    @Mock
    private AwardRepository awardRepository;
    @Mock
    private SuperheroMapper mapper;
    @Mock
    private UserRepository userRepository;

    private Superhero superhero;

    private SuperheroDto dto;

    @BeforeEach
    void setUp() {
        superhero = new Superhero();
        dto = new SuperheroDto();
    }

    @Test
    void getPage() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.unsorted());

        List<Superhero> superheroes = createListOf(5, Superhero::new);
        PageImpl<Superhero> pageResponse = new PageImpl<>(superheroes);

        when(repository.findAll(pageRequest)).thenReturn(pageResponse);
        when(mapper.toDto(any(Superhero.class))).thenReturn(new SuperheroDto());

        Page<SuperheroDto> page = service.getPage(pageRequest);

        assertThat(page.getContent()).hasSize(5);
    }

    @Test
    void getById() {
        Long id = 1L;
        when(repository.getOne(id)).thenReturn(superhero);
        when(mapper.toDto(superhero)).thenReturn(dto);

        SuperheroDto dtoResponse = service.getById(id);

        assertThat(dtoResponse).isEqualTo(dto);
    }

    @Test
    void create() {
        Superhero mockSuperhero = mock(Superhero.class);
        when(mapper.toEntity(dto)).thenReturn(mockSuperhero);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);
        when(mapper.toDto(mockSuperhero)).thenReturn(dto);

        User user = mock(User.class);
        String userId = "id";

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.getOne(userId)).thenReturn(user);

        SuperheroDto dtoResponse = service.create(dto, userId);

        assertThat(dtoResponse).isEqualTo(dto);

        verify(mockSuperhero).setId(null);
    }


    @Test
    void update() {
        Long id = 1L;
        when(repository.getOne(id)).thenReturn(superhero);

        when(repository.save(superhero)).thenReturn(superhero);

        when(mapper.toDto(superhero)).thenReturn(dto);

        SuperheroDto dtoResponse = service.update(id, dto);

        assertThat(dtoResponse)
                .isEqualTo(dto);

        verify(mapper).toEntityUpdate(dto, superhero);
    }

    @Test
    void delete() {
        Long id = 1L;

        service.delete(id);

        verify(repository).deleteById(id);
    }
}