package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.dto.KitchenwareCategoryCollectionDTO;
import com.edu.netc.bakensweets.dto.KitchenwareCategoryDTO;
import com.edu.netc.bakensweets.dto.KitchenwareDTO;
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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    public String createKitchenware(KitchenwareDTO kitchenwareDTO) {
        Collection<KitchenwareCategory> categories = kitchenwareRepository.getAllCategories();
        if (!categories.contains(new KitchenwareCategory(kitchenwareDTO.getCategory()))) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Category is invalid");
        }
            Kitchenware kitchenware = kitchenwareMapper.kitchenwareDTOtoKitchenware(kitchenwareDTO);
            kitchenwareRepository.create(kitchenware);
            return "Kitchenware has been added";
    }


}
