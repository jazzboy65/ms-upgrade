package alexgordeeff.ms_upgrade.service;


import alexgordeeff.ms_upgrade.model.ClientEntity;
import clients.model.ClientWithPageInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DaoClientService {

    boolean checkClientExistsById(UUID clientId);

    ClientEntity getClientById(UUID clientId);

    void deleteClientById(UUID clientId);

    void createClient(ClientEntity clientEntity);

    void updateClient(UUID clientId, ClientEntity clientEntity);

    List<ClientWithPageInfo> getClientPageById(Long mdmCode, Pageable pageable);
}
