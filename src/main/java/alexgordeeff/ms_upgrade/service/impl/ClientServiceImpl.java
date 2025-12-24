package alexgordeeff.ms_upgrade.service.impl;

import alexgordeeff.ms_upgrade.exception.ApiError;
import alexgordeeff.ms_upgrade.exception.ApiException;
import alexgordeeff.ms_upgrade.mapper.ClientMapper;
import alexgordeeff.ms_upgrade.model.ClientEntity;
import alexgordeeff.ms_upgrade.service.ClientService;
import alexgordeeff.ms_upgrade.service.DaoClientService;
import clients.model.Client;
import clients.model.ClientCreate;
import clients.model.ClientDTO;
import clients.model.ClientUpdate;
import clients.model.ClientsGet200Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientMapper mapper;
    private final DaoClientService daoClientService;

    @Override
    public ClientDTO getClientDTO(ClientEntity client) {
        return mapper.fromClientEntityToClientDTO(client);
    }

    @Override
    public Client fromClientEntityToClient(ClientEntity clientEntity) {
        return mapper.fromClientEntityToClient(clientEntity);
    }

    @Override
    public ClientEntity createClient(ClientCreate client) {
        var clientEntity = mapper.fromClientCreateToClientEntity(client);
        daoClientService.createClient(clientEntity);
        return clientEntity;
    }

    @Override
    public ClientEntity getClient(UUID clientId) {
        if (daoClientService.checkClientExistsById(clientId)) {
            return daoClientService.getClientById(clientId);
        } else {
            throw new ApiException(ApiError.NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public ClientEntity updateClient(UUID clientId, ClientUpdate update) {
        daoClientService.updateClient(clientId, update);
        return daoClientService.getClientById(clientId);
    }

    @Override
    public ClientsGet200Response fromPageableToClientWithPageInfo(Pageable pageable) {
        return daoClientService.getClientFromPageable(pageable);
    }

    @Override
    public void deleteClient(UUID clientId) {
        daoClientService.deleteClientById(clientId);
    }

    @Override
    public boolean clientExists(UUID clientId) {
        return daoClientService.checkClientExistsById(clientId);
    }
}
