package alexgordeeff.ms_upgrade.service.impl;

import alexgordeeff.ms_upgrade.exception.NotFoundException;
import alexgordeeff.ms_upgrade.mapper.MsUpgradeMapper;
import alexgordeeff.ms_upgrade.model.ClientEntity;
import alexgordeeff.ms_upgrade.service.ClientService;
import alexgordeeff.ms_upgrade.service.DaoClientService;
import clients.model.Client;
import clients.model.ClientCreate;
import clients.model.ClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final MsUpgradeMapper mapper;
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
    public ClientEntity createClient(ClientCreate clientCreate) {
        var clientEntity = mapper.fromClientCreateToClientEntity(clientCreate);
        daoClientService.createClient(clientEntity);
        return clientEntity;
    }

    @Override
    public ClientEntity getClient(UUID clientId) {
        if (daoClientService.checkClientExistsById(clientId)) {
            return daoClientService.getClientById(clientId);
        } else {
            throw new NotFoundException("Клиент не найден");
        }
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
