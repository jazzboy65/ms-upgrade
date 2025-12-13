package alexgordeeff.ms_upgrade.controller;

import alexgordeeff.ms_upgrade.service.ClientService;
import clients.api.ClientsApi;
import clients.model.CheckClientExists200Response;
import clients.model.ClientsGet200Response;
import clients.model.Client;
import clients.model.ClientCreate;
import clients.model.ClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MsUpgradeController implements ClientsApi {

    private final ClientService clientService;

    @Override
    public ResponseEntity<CheckClientExists200Response> checkClientExists(UUID clientId) {
        var response = new CheckClientExists200Response();
        response.setExists(clientService.clientExists(clientId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientsGet200Response> clientsGet(Integer page, Integer size, String lastName, Long mdmCode) {
        var client = clientService.getClientByPage(mdmCode, PageRequest.of(page, size));
        var response = new ClientsGet200Response();
        response.setContent(client);
        response.pageInfo()

        return null;
    }

    @Override
    public ResponseEntity<Client> createClient(ClientCreate clientCreate) {
        var clientEntity = clientService.createClient(clientCreate);
        var client = clientService.fromClientEntityToClient(clientEntity);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteClientById(UUID clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientDTO> getClientById(UUID clientId) {
        var clientDTO = clientService.getClientDTO(clientService.getClient(clientId));
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Client> updateClientById(UUID clientId, ClientCreate clientCreate) {
        var clientEntity = clientService.updateClient(clientId, clientCreate);
        var client = clientService.fromClientEntityToClient(clientEntity);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
}
