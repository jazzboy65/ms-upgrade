package alexgordeeff.ms_upgrade.controller;

import alexgordeeff.ms_upgrade.service.ClientService;
import alexgordeeff.ms_upgrade.service.ConversionRatesService;
import clients.api.ClientsApi;
import clients.model.CheckClientExists200Response;
import clients.model.ClientsGet200Response;
import clients.model.Client;
import clients.model.ClientCreate;
import clients.model.ClientDTO;
import clients.model.ClientUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class ClientController implements ClientsApi {

    private final ClientService clientService;
    private final ConversionRatesService conversionRatesService;

    @Override
    public ResponseEntity<CheckClientExists200Response> checkClientExists(UUID clientId) {
        var response = new CheckClientExists200Response();
        response.setExists(clientService.clientExists(clientId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientsGet200Response> clientsGet(Integer page, Integer size, String lastName, Long mdmCode) {
        var pageable = PageRequest.of(page, size,Sort.by("lastName", "mdmCode").descending());
        var response = clientService.fromPageableToClientWithPageInfo(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Client> createClient(ClientCreate clientCreate) {
        var clientEntity = clientService.createClient(clientCreate);
        var response = clientService.fromClientEntityToClient(clientEntity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteClientById(UUID clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientDTO> getClientById(UUID clientId) {
        var response = clientService.getClientDTO(clientService.getClient(clientId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Client> updateClientById(UUID clientId, ClientUpdate client) {
        var clientEntity = clientService.updateClient(clientId, client);
        var response = clientService.fromClientEntityToClient(clientEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getRate")
    public ResponseEntity<String> getExchangeRate(
            @RequestParam(name = "fromRate") String fromRate,
            @RequestParam(name = "toRate") String toRate) {
        var rate = conversionRatesService.convert(fromRate, toRate);
        return new ResponseEntity<>(String.format("Курс %s к %s = %f", fromRate, toRate, rate), HttpStatus.OK);
    }
}
