package alexgordeeff.ms_upgrade.service;


import alexgordeeff.ms_upgrade.model.ClientEntity;
import clients.model.ClientUpdate;
import clients.model.ClientsGet200Response;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DaoClientService {

    boolean checkClientExistsById(UUID clientId);

    ClientEntity getClientById(UUID clientId);

    void deleteClientById(UUID clientId);

    void createClient(ClientEntity clientEntity);

    void updateClient(UUID clientId, ClientUpdate clientEntity);

    ClientsGet200Response getClientFromPageable(Pageable pageable);
}
