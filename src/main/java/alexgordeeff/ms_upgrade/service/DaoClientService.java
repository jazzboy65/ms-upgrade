package alexgordeeff.ms_upgrade.service;


import alexgordeeff.ms_upgrade.model.ClientEntity;

import java.util.UUID;

public interface DaoClientService {

    boolean checkClientExistsById(UUID clientId);

    ClientEntity getClientById(UUID clientId);

    void deleteClientById(UUID clientId);

    void createClient(ClientEntity clientEntity);
}
