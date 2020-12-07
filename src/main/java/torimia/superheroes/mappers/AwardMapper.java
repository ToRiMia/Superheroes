package torimia.superheroes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.model.dto.AwardDto;
import torimia.superheroes.model.entity.Award;

import javax.swing.text.TabExpander;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    AwardDto toDto(Award award);

    Award toEntity(AwardDto awardDto);

    @Mapping(target = "id", ignore = true)
    void toEntityUpdate(AwardDto awardDto, @MappingTarget Award award);
}
