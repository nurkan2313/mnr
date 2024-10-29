package kg.core.mnr.models.mapper;

import kg.core.mnr.models.dto.requests.CitesPermitFormRequest;
import kg.core.mnr.models.entity.CitesPermit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CitesPermitMapper {

    // Маппинг от формы к сущности
    @Mapping(target = "id", ignore = true) // id не передается из формы, он будет генерироваться
    CitesPermit toEntity(CitesPermitFormRequest dto);

//    // Маппинг от сущности к форме (например, для отображения в UI)
//    CitesPermitFormRequest toDto(CitesPermit entity);
}
