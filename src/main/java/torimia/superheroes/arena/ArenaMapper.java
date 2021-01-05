package torimia.superheroes.arena;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.dto.ArenaBattleView;
import torimia.superheroes.arena.model.entity.Arena;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.model.dto.SuperheroDto;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForTop;
import torimia.superheroes.superhero.model.dto.SuperheroViewForTop;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mapper(imports = {Collectors.class}, componentModel = "spring", uses = SuperheroMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ArenaMapper {

    ArenaBattleDto toDto(Arena arena);

    @Mapping(target = "fightStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    Arena toEntity(ArenaBattleDto dto);

    @Mapping(target = "fightStatus",ignore = true)
    void toEntityUpdate(ArenaBattleDto dto, @MappingTarget Arena battle);

    ArrayList<ArenaBattleDto> toDtoFromView(ArrayList<ArenaBattleView> arena);
}
