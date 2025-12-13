package alexgordeeff.ms_upgrade.service;

import alexgordeeff.ms_upgrade.model.ClientEntity;
import clients.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ClientDTO getClientDTO(ClientEntity clientEntity);

    ClientEntity createClient(ClientCreate clientCreate);

    boolean clientExists(UUID clientId);

    ClientEntity getClient(UUID clientId);

    void deleteClient(UUID clientId);

    Client fromClientEntityToClient(ClientEntity clientEntity);

    ClientEntity updateClient(UUID clientId, ClientCreate client);

    List<ClientWithPageInfo> getClientByPage(Long mdmCode, Pageable pageable);
}
