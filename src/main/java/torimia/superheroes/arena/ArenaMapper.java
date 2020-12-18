package torimia.superheroes.arena;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.entity.Arena;
import torimia.superheroes.superhero.SuperheroMapper;

import java.util.stream.Collectors;

@Mapper(imports = {Collectors.class}, componentModel = "spring", uses = SuperheroMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ArenaMapper {


    ArenaBattleDto toDto(Arena arena);

    @Mapping(target = "id", ignore = true)
    Arena toEntity(ArenaBattleDto dto);
}
