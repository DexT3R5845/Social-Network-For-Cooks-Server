package com.edu.netc.bakensweets.mapper;

import com.edu.netc.bakensweets.dto.KitchenwareDTO;
import com.edu.netc.bakensweets.model.Kitchenware;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Mapper(componentModel = "spring")
public interface KitchenwareMapper {
    @Mappings({
            @Mapping(target="id", source="kitchenwareDTO.id"),
            @Mapping(target="name", source="kitchenwareDTO.name"),
            @Mapping(target = "imgUrl", source = "kitchenwareDTO.imgUrl"),
            @Mapping(target = "category", source = "kitchenwareDTO.category"),
            @Mapping(target = "active", source = "kitchenwareDTO.active")
    })
    Kitchenware kitchenwareDTOtoKitchenware(KitchenwareDTO kitchenwareDTO);
    @Mappings({
            @Mapping(target="id", source="kitchenware.id"),
            @Mapping(target="name", source="kitchenware.name"),
            @Mapping(target = "imgUrl", source = "kitchenware.imgUrl"),
            @Mapping(target = "category", source = "kitchenware.category"),
            @Mapping(target = "active", source = "kitchenware.active")
    })
    KitchenwareDTO kitchenwaretoKitchenwareDTO(Kitchenware kitchenware);

    @Mappings({
            @Mapping(target="id", source="kitchenwarePage.id"),
            @Mapping(target="name", source="kitchenwarePage.name"),
            @Mapping(target = "imgUrl", source = "kitchenwarePage.imgUrl"),
            @Mapping(target = "category", source = "kitchenwarePage.category"),
            @Mapping(target = "active", source = "kitchenwarePage.active")
    })
    Collection<KitchenwareDTO> kitchenwarePageToDtoCollection (Collection<Kitchenware> kitchenwarePage);
}