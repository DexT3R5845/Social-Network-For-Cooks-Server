package com.edu.netc.bakensweets.mapperConfig;

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
            @Mapping(target="kitchenwareName", source="kitchenwareDTO.name"),
            @Mapping(target = "kitchenwareImg", source = "kitchenwareDTO.imgUrl"),
            @Mapping(target = "kitchenwareCategory", source = "kitchenwareDTO.category"),
            @Mapping(target = "active", source = "kitchenwareDTO.active")
    })
    Kitchenware kitchenwareDTOtoKitchenware(KitchenwareDTO kitchenwareDTO);
    @Mappings({
            @Mapping(target="id", source="kitchenware.id"),
            @Mapping(target="name", source="kitchenware.kitchenwareName"),
            @Mapping(target = "imgUrl", source = "kitchenware.kitchenwareImg"),
            @Mapping(target = "category", source = "kitchenware.kitchenwareCategory"),
            @Mapping(target = "active", source = "kitchenware.active")
    })
    KitchenwareDTO kitchenwaretoKitchenwareDTO(Kitchenware kitchenware);

    @Mappings({
            @Mapping(target="id", source="kitchenwarePage.id"),
            @Mapping(target="name", source="kitchenwarePage.kitchenwareName"),
            @Mapping(target = "imgUrl", source = "kitchenwarePage.kitchenwareImg"),
            @Mapping(target = "category", source = "kitchenwarePage.kitchenwareCategory"),
            @Mapping(target = "active", source = "kitchenwarePage.active")
    })
    Collection<KitchenwareDTO> kitchenwarePageToDtoCollection (Collection<Kitchenware> kitchenwarePage);
}