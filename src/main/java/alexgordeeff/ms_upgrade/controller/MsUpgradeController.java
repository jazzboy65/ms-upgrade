package alexgordeeff.ms_upgrade.controller;

import alexgordeeff.ms_upgrade.service.MsUpgradeService;
import clients.api.ClientsApi;
import clients.model.CheckClientExists200Response;
import clients.model.ClientsGet200Response;
import clients.model.Client;
import clients.model.ClientCreate;
import clients.model.ClientDTO;
import clients.model.ClientData;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class MsUpgradeController implements ClientsApi{

    private final MsUpgradeService msUpgradeService;


    @Override
    public ResponseEntity<CheckClientExists200Response> checkClientExists(
            @NotNull @PathVariable("clientId") UUID clientId) {
            var response = new CheckClientExists200Response();
            response.setExists(msUpgradeService.checkClientExists(clientId));
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientsGet200Response> clientsGet(Integer page, Integer size, String lastName, Integer mdmId) {
        return null;
    }

    @Override
    public ResponseEntity<Client> createClient(ClientCreate clientCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteClientById(String clientId) {
        return null;
    }

    @Override
    public ResponseEntity<ClientDTO> getClientById(UUID clientId) {
        return null;
    }

    @Override
    public ResponseEntity<Client> updateClientById(UUID clientId, ClientData clientData) {
        return null;
    }
}
