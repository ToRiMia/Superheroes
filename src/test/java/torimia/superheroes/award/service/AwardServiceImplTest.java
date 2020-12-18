package torimia.superheroes.award.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import torimia.superheroes.award.AwardMapper;
import torimia.superheroes.award.AwardRepository;
import torimia.superheroes.award.model.dto.AwardDto;
import torimia.superheroes.award.model.entity.Award;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwardServiceImplTest {

    @InjectMocks
    private AwardServiceImpl service;
    @Mock
    private AwardRepository repository;
    @Mock
    private AwardMapper mapper;

    private Award award;

    private AwardDto dto;

    @BeforeEach
    void setUp() {
        award = new Award();
        dto = new AwardDto();
    }

    @Test
    void getPage() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.unsorted());

        List<Award> awards = Arrays.asList(new Award(), new Award(),
                new Award(), new Award(), new Award());
        PageImpl<Award> pageResponse = new PageImpl<>(awards);
        when(repository.findAll(pageRequest)).thenReturn(pageResponse);

        when(mapper.toDto(any(Award.class))).thenReturn(new AwardDto());

        Page<AwardDto> page = service.getPage(pageRequest);

        assertThat(page.getContent()).hasSize(5);
    }

    @Test
    void getById() {
        Long id = 1L;
        when(repository.getOne(id)).thenReturn(award);
        when(mapper.toDto(award)).thenReturn(dto);

        AwardDto dtoResponse = service.getById(id);

        assertThat(dtoResponse).isEqualTo(dto);
    }

    @Test
    void create() {
        Award mockAward = mock(Award.class);
        when(mapper.toEntity(dto)).thenReturn(mockAward);

        when(repository.save(mockAward)).thenReturn(mockAward);
        when(mapper.toDto(mockAward)).thenReturn(dto);

        AwardDto dtoResponse = service.create(dto);

        assertThat(dtoResponse).isEqualTo(dto);

        verify(mockAward).setId(null);
    }

    @Test
    void update() {
        Long id = 1L;
        when(repository.getOne(id)).thenReturn(award);

        when(repository.save(award)).thenReturn(award);

        when(mapper.toDto(award)).thenReturn(dto);

        AwardDto dtoResponse = service.update(id, dto);

        assertThat(dtoResponse).isEqualTo(dto);

        verify(mapper).toEntityUpdate(dto, award);
    }

    @Test
    void delete() {
        Long id = 1L;

        service.delete(id);

        verify(repository).deleteById(id);
    }
}