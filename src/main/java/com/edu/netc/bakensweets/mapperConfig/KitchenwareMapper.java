package com.edu.netc.bakensweets.mapperConfig;

import com.edu.netc.bakensweets.dto.*;
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
            @Mapping(target="kitchwarName", source="kitchenwareDTO.name"),
            @Mapping(target = "kitchwarImg", source = "kitchenwareDTO.imgUrl"),
            @Mapping(target = "kitchwarCategory", source = "kitchenwareDTO.category"),
            @Mapping(target = "active", source = "kitchenwareDTO.active")
    })
    Kitchenware kitchenwareDTOtoKitchenware(KitchenwareDTO kitchenwareDTO);
    @Mappings({
            @Mapping(target="id", source="kitchenware.id"),
            @Mapping(target="name", source="kitchenware.kitchwarName"),
            @Mapping(target = "imgUrl", source = "kitchenware.kitchwarImg"),
            @Mapping(target = "category", source = "kitchenware.kitchwarCategory"),
            @Mapping(target = "active", source = "kitchenware.active")
    })
    KitchenwareDTO kitchenwaretoKitchenwareDTO(Kitchenware kitchenware);

    @Mappings({
            @Mapping(target="id", source="kitchenwarePage.id"),
            @Mapping(target="name", source="kitchenwarePage.kitchwarName"),
            @Mapping(target = "imgUrl", source = "kitchenwarePage.kitchwarImg"),
            @Mapping(target = "category", source = "kitchenwarePage.kitchwarCategory"),
            @Mapping(target = "active", source = "kitchenwarePage.active")
    })
    Collection<KitchenwareDTO> kitchenwarePageToDtoCollection (Collection<Kitchenware> kitchenwarePage);
}