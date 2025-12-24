package alexgordeeff.ms_upgrade.service;

import alexgordeeff.ms_upgrade.model.ClientEntity;
import clients.model.ClientDTO;
import clients.model.ClientCreate;
import clients.model.Client;
import clients.model.ClientUpdate;
import clients.model.ClientsGet200Response;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClientService {

    ClientDTO getClientDTO(ClientEntity clientEntity);

    ClientEntity createClient(ClientCreate clientCreate);

    boolean clientExists(UUID clientId);

    ClientEntity getClient(UUID clientId);

    void deleteClient(UUID clientId);

    Client fromClientEntityToClient(ClientEntity clientEntity);

    ClientEntity updateClient(UUID clientId, ClientUpdate client);

    ClientsGet200Response fromPageableToClientWithPageInfo(Pageable clientEntity);
}
