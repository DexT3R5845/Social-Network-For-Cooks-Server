package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.*;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.mapperConfig.CredentialsMapper;
import com.edu.netc.bakensweets.mapperConfig.KitchenwareMapper;
import com.edu.netc.bakensweets.model.*;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.repository.interfaces.FriendshipRepository;
import com.edu.netc.bakensweets.repository.interfaces.KitchenwareRepository;
import com.edu.netc.bakensweets.security.JwtTokenProvider;
import com.edu.netc.bakensweets.service.interfaces.CaptchaService;
import com.edu.netc.bakensweets.service.interfaces.FriendshipService;

import com.edu.netc.bakensweets.service.interfaces.KitchenwareService;
import com.edu.netc.bakensweets.service.interfaces.WrongAttemptLoginService;
import com.edu.netc.bakensweets.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Service
public class KitchenwareServiceImpl implements KitchenwareService {
    private final KitchenwareRepository kitchenwareRepository;
    private final KitchenwareMapper kitchenwareMapper;

//    public KitchenwareServiceImpl(KitchenwareRepository kitchenwareRepository,
//                                  KitchenwareMapper kitchenwareMapper) {
//        this.kitchenwareRepository = kitchenwareRepository;
//        this.kitchenwareMapper = kitchenwareMapper;
//    }

    @Override
    public KitchenwareCategoryCollectionDTO getAllCategories() {
        Collection<KitchenwareCategory> categories = kitchenwareRepository.getAllCategories();
        return new KitchenwareCategoryCollectionDTO(
                kitchenwareMapper.kitchenwareCategoriesToDtoCollection(categories));
    }


    @Override
    public KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO) {
        Collection<KitchenwareCategory> categories = kitchenwareRepository.getAllCategories();
        if (!categories.contains(new KitchenwareCategory(kitchenwareDTO.getCategory()))) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Category is invalid");
        }
            Kitchenware kitchenware = kitchenwareMapper.kitchenwareDTOtoKitchenware(kitchenwareDTO);
        Long id = Utils.generateUniqueId();
        kitchenware.setId(id);
        kitchenwareRepository.create(kitchenware);
        kitchenwareDTO.setId(id.toString());
        kitchenwareDTO.setActive(true);
        return kitchenwareDTO;
    }

    @Override
    public ItemsPerPageDTO<KitchenwareDTO> getFilteredKitchenware(String name, List<Object> args, int limit, boolean order, int currentPage) {
        int count = kitchenwareRepository.countFilteredKitchenware(name, args);
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        if (limit < 1 || currentPage < 1 || currentPage > pageCount) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid params");
        }
        Collection<Kitchenware> kitchenwarePage = kitchenwareRepository.filterKitchenware(
                name, args, limit,  (currentPage - 1) * limit, order
        );
        return new ItemsPerPageDTO<KitchenwareDTO>(
                kitchenwareMapper.kitchenwarePageToDtoCollection(kitchenwarePage), currentPage, count
        );
    }


}
