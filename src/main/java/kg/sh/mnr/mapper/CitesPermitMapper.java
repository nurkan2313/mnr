package kg.sh.mnr.mapper;

import kg.sh.mnr.entity.CitesPermit;
import kg.sh.mnr.model.requests.CitesPermitFormRequest;
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
