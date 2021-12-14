package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.KitchenwareDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.exception.BadRequestParamException;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.KitchenwareMapper;
import com.edu.netc.bakensweets.model.Kitchenware;
import com.edu.netc.bakensweets.repository.interfaces.KitchenwareRepository;
import com.edu.netc.bakensweets.service.interfaces.KitchenwareService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@AllArgsConstructor
@Service
public class KitchenwareServiceImpl implements KitchenwareService {
    private final KitchenwareRepository kitchenwareRepository;
    private final KitchenwareMapper kitchenwareMapper;

    @Override
    @Transactional
    public Collection<String> getAllCategories() {
        return kitchenwareRepository.getAllCategories();
    }


    @Override
    @Transactional
    public KitchenwareDTO getKitchenwareById(Long id) {
        try {
            return kitchenwareMapper.kitchenwaretoKitchenwareDTO(kitchenwareRepository.findById(id));
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Kitchenware with id %s not found.", id));
        }
    }

    @Override
    @Transactional
    public KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO) {
        try {
            Kitchenware kitchenware = kitchenwareMapper.kitchenwareDTOtoKitchenware(kitchenwareDTO);
            long id = kitchenwareRepository.create(kitchenware);
            kitchenwareDTO.setId(String.valueOf(id));
            kitchenwareDTO.setActive(true);
            return kitchenwareDTO;

        } catch (
                DataIntegrityViolationException ex) {
            throw new BadRequestParamException("category", "Category is invalid");
        }
    }

    @Override
    @Transactional
    public KitchenwareDTO updateKitchenware(KitchenwareDTO kitchenwareDTO, long id) {
        try {
            kitchenwareDTO.setId(String.valueOf(id));
            Kitchenware kitchenware = kitchenwareMapper.kitchenwareDTOtoKitchenware(kitchenwareDTO);
            boolean updated = kitchenwareRepository.update(kitchenware);
            if (!updated) {
                throw new CustomException(HttpStatus.NOT_FOUND, String.format("Kitchenware with id %s not found.", id));
            }
            return kitchenwareDTO;
        } catch (
                DataIntegrityViolationException ex) {
            throw new BadRequestParamException("category", "Category is invalid");
        }
    }

    @Override
    @Transactional
    public void changeKitchenwareStatus(long id) {
        boolean updated = kitchenwareRepository.changeStatusById(id);
        if (!updated) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Kitchenware with id %s not found.", id));
        }
    }

    @Override
    @Transactional
    public PaginationDTO<KitchenwareDTO> getFilteredKitchenware(String name, Collection<String> args, Boolean active, int limit, boolean order, int currentPage) {
        int count = kitchenwareRepository.countFilteredKitchenware(name, args, active);
        Collection<Kitchenware> kitchenwareCollection = kitchenwareRepository.filterKitchenware(
                name, args, active, limit, currentPage * limit, order
        );
        return new PaginationDTO<>(
                kitchenwareMapper.kitchenwarePageToDtoCollection(kitchenwareCollection), count
        );
    }
}
