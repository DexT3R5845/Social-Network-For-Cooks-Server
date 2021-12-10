package com.edu.netc.bakensweets.mapperConfig;

import com.edu.netc.bakensweets.dto.UpdateAccountDTO;
import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mappings({
            @Mapping(target="firstName", source="dto.firstName"),
            @Mapping(target="lastName", source="dto.lastName"),
            @Mapping(target = "birthDate", source = "dto.birthDate"),
            @Mapping(target = "gender", source = "dto.gender")
    })
    Account accountDTOtoAccount(AccountDTO dto);

    @Mappings({
            @Mapping(target = "id", source = "infoDto.id"),
            @Mapping(target="firstName", source="infoDto.firstName"),
            @Mapping(target="lastName", source="infoDto.lastName"),
            @Mapping(target = "birthDate", source = "infoDto.birthDate"),
            @Mapping(target = "gender", source = "infoDto.gender"),
            @Mapping(target = "imgUrl", source = "infoDto.imgUrl")
    })
    Account accountPersonalInfoDTOtoAccounts(AccountPersonalInfoDTO infoDto);

    @Mappings({
            @Mapping(target = "id", source = "account.id"),
            @Mapping(target="firstName", source="account.firstName"),
            @Mapping(target="lastName", source="account.lastName"),
            @Mapping(target = "birthDate", source = "account.birthDate"),
            @Mapping(target = "gender", source = "account.gender"),
            @Mapping(target = "imgUrl", source = "account.imgUrl"),
            @Mapping(target = "status", source = "account.status")
    })
    AccountPersonalInfoDTO accountToAccountPersonalInfoDto(Account account);

    @Mappings({
            @Mapping(target = "id", source = "accounts.id"),
            @Mapping(target="firstName", source="accounts.firstName"),
            @Mapping(target="lastName", source="accounts.lastName"),
            @Mapping(target = "birthDate", source = "accounts.birthDate"),
            @Mapping(target = "gender", source = "accounts.gender"),
            @Mapping(target = "imgUrl", source = "accounts.imgUrl"),
            @Mapping(target = "status", source = "accounts.status")
    })
    Collection<AccountPersonalInfoDTO> accountsToPersonalInfoDtoCollection (Collection<Account> accounts);

    @Mappings({
            @Mapping(target="firstName", source="updateDTO.firstName"),
            @Mapping(target="lastName", source="updateDTO.lastName"),
            @Mapping(target = "birthDate", source = "updateDTO.birthDate", dateFormat = "dd/MM/yyyy"),
            @Mapping(target = "gender", source = "updateDTO.gender"),
            @Mapping(target = "imgUrl", source = "updateDTO.imgUrl")
    })
    Account updateAccountDTOtoAccount(UpdateAccountDTO updateDTO);
    @Mappings({
            @Mapping(target="firstName", source="entity.firstName"),
            @Mapping(target="lastName", source="entity.lastName"),
            @Mapping(target = "birthDate", source = "entity.birthDate", dateFormat = "dd/MM/yyyy"),
            @Mapping(target = "gender", source = "entity.gender"),
            @Mapping(target = "imgUrl", source = "entity.imgUrl")
    })
    UpdateAccountDTO accountToUpdateAccountDTO(Account entity);
}
