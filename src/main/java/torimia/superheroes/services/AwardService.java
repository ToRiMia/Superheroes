package torimia.superheroes.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import torimia.superheroes.model.dto.AwardDto;

public interface AwardService {

    Page<AwardDto> getPage(Pageable page);

    AwardDto getById(Long id);

    AwardDto create(AwardDto dto);

    AwardDto update(Long id, AwardDto dto);

    void delete(Long id);
}
