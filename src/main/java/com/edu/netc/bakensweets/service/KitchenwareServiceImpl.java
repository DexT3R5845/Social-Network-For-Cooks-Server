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

    @Override
    public Collection<String> getAllCategories() {
        return kitchenwareRepository.getAllCategories();
    }

    private void checkKitchenwareDTO(KitchenwareDTO kitchenwareDTO) {
        Collection<String> categories = kitchenwareRepository.getAllCategories();
        if (kitchenwareDTO.getCategory() == null || kitchenwareDTO.getName() == null || kitchenwareDTO.getImgUrl() == null) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid params");
        }
        if (!categories.contains(kitchenwareDTO.getCategory())) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Category is invalid");
        }
        if (kitchenwareDTO.getId() != null && !kitchenwareDTO.getId().matches("^[0-9]+$")) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Id is invalid");
        }
    }

    private void checkId(String id) {
        if (id == null) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "No id provided");
        }
        else if (!id.matches("^[0-9]+$")) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Id is invalid");
        }
    }

    @Override
    public KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO) {
        checkKitchenwareDTO(kitchenwareDTO);
        Kitchenware kitchenware = kitchenwareMapper.kitchenwareDTOtoKitchenware(kitchenwareDTO);
        Long id = Utils.generateUniqueId();
        kitchenware.setId(id);
        kitchenwareRepository.create(kitchenware);
        kitchenwareDTO.setId(id.toString());
        kitchenwareDTO.setActive(true);
        return kitchenwareDTO;
    }

    @Override
    public KitchenwareDTO updateKitchenware(KitchenwareDTO kitchenwareDTO) {
        checkKitchenwareDTO(kitchenwareDTO);
        checkId(kitchenwareDTO.getId());
        Kitchenware kitchenware = kitchenwareMapper.kitchenwareDTOtoKitchenware(kitchenwareDTO);
        kitchenwareRepository.update(kitchenware);
        return kitchenwareDTO;
    }

    @Override
    public KitchenwareDTO deleteKitchenware(String id) {
        checkId(id);
        kitchenwareRepository.deleteById(Long.valueOf(id));
        Kitchenware kitchenware = kitchenwareRepository.findById(Long.valueOf(id));
        KitchenwareDTO kitchenwareDTO = kitchenwareMapper.kitchenwaretoKitchenwareDTO(kitchenware);
        return kitchenwareDTO;
    }

    @Override
    public KitchenwareDTO reactivateKitchenware(String id) {
        checkId(id);
        kitchenwareRepository.reactivateById(Long.valueOf(id));
        Kitchenware kitchenware = kitchenwareRepository.findById(Long.valueOf(id));
        KitchenwareDTO kitchenwareDTO = kitchenwareMapper.kitchenwaretoKitchenwareDTO(kitchenware);
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
