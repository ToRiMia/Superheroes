package torimia.superheroes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import torimia.superheroes.mappers.AwardMapper;
import torimia.superheroes.model.dto.AwardDto;
import torimia.superheroes.model.entity.Award;
import torimia.superheroes.repo.AwardRepository;
@RequiredArgsConstructor
@Service
public class AwardServiceInput implements AwardService{

    private final AwardRepository repository;
    private final AwardMapper mapper;

    @Override
    public Page<AwardDto> getPage(Pageable page) {
        Page<Award> awards = repository.findAll(page);
        return awards.map(mapper::toDto);
    }

    @Override
    public AwardDto getById(Long id) {
        return mapper.toDto(repository.getOne(id));
    }

    @Override
    public AwardDto create(AwardDto dto) {
        Award award = mapper.toEntity(dto);
        award.setId(null);
        return mapper.toDto(repository.save(award));
    }

    @Override
    public AwardDto update(Long id, AwardDto dto) {
        Award award = repository.getOne(id);
        mapper.toEntityUpdate(dto, award);
        return mapper.toDto(repository.save(award));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
