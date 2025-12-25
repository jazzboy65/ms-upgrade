package alexgordeeff.ms_upgrade.mapper;


import alexgordeeff.ms_upgrade.model.ClientEntity;
import clients.model.Client;
import clients.model.ClientCreate;
import clients.model.ClientDTO;
import clients.model.ClientPageDto;
import clients.model.ClientsGet200Response;
import clients.model.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    @Mapping(target = "createdAt",source = "creationDate")
    @Mapping(target = "updatedAt", source = "updatedDate")
    @Mapping(target = "status", source = "accountStatus.name")
    ClientDTO fromClientEntityToClientDTO(ClientEntity clientId);

    @Mapping(target = "hasAccounts", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "accountStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    ClientEntity fromClientCreateToClientEntity(ClientCreate client);

    @Mapping(target = "createdAt",source = "creationDate")
    @Mapping(target = "updatedAt", source = "updatedDate")
    @Mapping(target = "status", source = "accountStatus.name")
    Client fromClientEntityToClient(ClientEntity clientEntity);

    @Mapping(target = "status", source = "accountStatus.name")
    ClientPageDto fromClientEntityToClientWithPage(ClientEntity client);

    @Mapping(target = "pageInfo", source = ".", qualifiedByName = "pageToPageInfo")
    ClientsGet200Response fromClientEntityToClientWithPageInfo(Page<ClientEntity> clientEntity);

    @Named("pageToPageInfo")
    default PageInfo pageToPageInfo(Page<ClientEntity> page) {
        var pageInfo = new PageInfo();
        pageInfo.totalPages(page.getTotalPages());
        pageInfo.pageSize(page.getSize());
        pageInfo.pageNumber(page.getNumber());
        pageInfo.totalElements(page.getTotalElements());
        return pageInfo;
    }
}


