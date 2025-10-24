package alexgordeeff.ms_upgrade.service;

import alexgordeeff.ms_upgrade.model.ClientEntity;
import clients.model.Client;
import clients.model.ClientCreate;
import clients.model.ClientDTO;

import java.util.UUID;

public interface ClientService {

    ClientDTO getClientDTO(ClientEntity clientEntity);

    ClientEntity createClient(ClientCreate clientCreate);

    boolean clientExists(UUID clientId);

    ClientEntity getClient(UUID clientId);

    void deleteClient(UUID clientId);

    Client fromClientEntityToClient(ClientEntity clientEntity);
}
