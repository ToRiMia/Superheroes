package torimia.superheroes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.model.dto.AwardDto;
import torimia.superheroes.model.dto.AwardView;
import torimia.superheroes.model.entity.Award;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    AwardDto toDto(Award award);

    @Mapping(target = "superhero", ignore = true)
    Award toEntity(AwardDto awardDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "superhero", ignore = true)
    void toEntityUpdate(AwardDto awardDto, @MappingTarget Award award);

    AwardDto toAwardsDto(AwardView awards);

    default List<Long> toIdsAwards(Set<Award> awards) {
        return awards.stream().map(Award::getId).collect(Collectors.toList());
    }

}
