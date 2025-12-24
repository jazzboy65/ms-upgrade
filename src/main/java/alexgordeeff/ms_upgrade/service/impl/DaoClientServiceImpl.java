package alexgordeeff.ms_upgrade.service.impl;

import alexgordeeff.ms_upgrade.exception.BadRequestException;
import alexgordeeff.ms_upgrade.exception.ConflictException;
import alexgordeeff.ms_upgrade.exception.NotFoundException;
import alexgordeeff.ms_upgrade.mapper.ClientMapper;
import alexgordeeff.ms_upgrade.model.ClientEntity;
import alexgordeeff.ms_upgrade.model.ClientStatus;
import alexgordeeff.ms_upgrade.repository.AccountStatusRepository;
import alexgordeeff.ms_upgrade.repository.ClientRepository;
import alexgordeeff.ms_upgrade.service.DaoClientService;
import clients.model.ClientUpdate;
import clients.model.ClientsGet200Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DaoClientServiceImpl implements DaoClientService {

    private final ClientRepository clientRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public boolean checkClientExistsById(UUID clientId) {
        if (clientRepository.existsById(clientId)) {
            return true;
        } else throw new NotFoundException("Клиент не найден");
    }

    @Override
    @Transactional
    public void deleteClientById(UUID clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Клиент не найден");
        } else if (clientRepository.getReferenceById(clientId).getHasAccounts()) {
            throw new ConflictException("У клиента есть активные счета");
        } else {
            var clientEntity = clientRepository.findClientById(clientId);
            clientEntity.setAccountStatus(accountStatusRepository.findByName(ClientStatus.DELETED));
        }
    }

    @Override
    @Transactional
    public void createClient(ClientEntity clientEntity) {
        if (!clientRepository.existsByMdmCode(clientEntity.getMdmCode())) {
            clientEntity.setCreationDate(OffsetDateTime.now());
            clientEntity.setAccountStatus(accountStatusRepository.findByName(ClientStatus.ACTIVE));
            clientRepository.saveAndFlush(clientEntity);
        } else if (clientRepository.existsByMdmCode(clientEntity.getMdmCode())) {
            throw new ConflictException("Клиент с таким mdmId уже существует");
        } else throw new BadRequestException("Невалидные данные");
    }

    @Override
    @Transactional
    public ClientEntity getClientById(UUID clientId) {
        if (clientRepository.existsById(clientId)) {
            return clientRepository.findClientById(clientId);
        } else throw new NotFoundException("Клиент не найден");
    }

    @Override
    @Transactional
    public ClientsGet200Response getClientFromPageable(Pageable pageable) {
            return clientMapper.fromClientEntityToClientWithPageInfo(clientRepository.findAll(pageable));
    }

    @Override
    @Transactional
    public void updateClient(UUID clientId, ClientUpdate clientUpdate) {
        if (clientRepository.existsById(clientId)) {
            var client = clientRepository.findClientById(clientId);
            client.setUpdatedDate(OffsetDateTime.now());
            if(clientUpdate.getFirstName() != null) {
                client.setFirstName(clientUpdate.getFirstName());
            }
            if(clientUpdate.getMiddleName() != null) {
                client.setMiddleName(clientUpdate.getMiddleName());
            }
            if(clientUpdate.getLastName() != null) {
                client.setLastName(clientUpdate.getLastName());
            }
            clientRepository.saveAndFlush(client);
        } else if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Клиент не найден");
        } else throw new BadRequestException("Невалидные данные");
    }
}
