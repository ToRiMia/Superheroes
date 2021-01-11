package torimia.superheroes.arena;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.arena.model.dto.BattleDtoResultFromServerDto;
import torimia.superheroes.arena.model.entity.Battle;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.Superhero;

import java.util.stream.Collectors;

@Mapper(imports = {Collectors.class, Superhero.class}, componentModel = "spring", uses = {SuperheroMapper.class, SuperheroRepository.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BattleMapper {

    @Mapping(target = "winnerId", source = "winner")
    BattleDtoResultFromServerDto toDto(Battle battle);

    @Mapping(target = "winner", source = "winnerId")
    @Mapping(target = "battleStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    Battle toEntity(BattleDtoResultFromServerDto dto);

    @Mapping(target = "winner", source = "winnerId")
    @Mapping(target = "battleStatus",ignore = true)
    void toEntityUpdate(BattleDtoResultFromServerDto dto, @MappingTarget Battle battle);
}
