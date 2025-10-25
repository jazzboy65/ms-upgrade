package alexgordeeff.ms_upgrade.mapper;


import alexgordeeff.ms_upgrade.model.ClientEntity;
import clients.model.Client;
import clients.model.ClientCreate;
import clients.model.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MsUpgradeMapper {

    @Mapping(target = "createdAt",source = "creationDate")
    @Mapping(target = "updatedAt", source = "updatedDate")
    @Mapping(target = "mdmId", source = "mdmCode")
    @Mapping(target = "status", source = "accountStatus.name")
    ClientDTO fromClientEntityToClientDTO(ClientEntity clientId);

    @Mapping(target = "hasAccounts", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "accountStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    ClientEntity fromClientCreateToClientEntity(ClientCreate clientCreate);

    @Mapping(target = "createdAt",source = "creationDate")
    @Mapping(target = "updatedAt", source = "updatedDate")
    @Mapping(target = "status", source = "accountStatus.name")
    @Mapping(target = "mdmId", source = "mdmCode")
    Client fromClientEntityToClient(ClientEntity clientEntity);
}


