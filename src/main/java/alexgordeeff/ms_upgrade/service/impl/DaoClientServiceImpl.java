package alexgordeeff.ms_upgrade.service.impl;

import alexgordeeff.ms_upgrade.exception.BadRequestException;
import alexgordeeff.ms_upgrade.exception.ConflictException;
import alexgordeeff.ms_upgrade.exception.NotFoundException;
import alexgordeeff.ms_upgrade.model.ClientEntity;
import alexgordeeff.ms_upgrade.repository.ClientRepository;
import alexgordeeff.ms_upgrade.service.DaoClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DaoClientServiceImpl implements DaoClientService {

    private final ClientRepository clientRepository;

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
        } else clientRepository.deleteById(clientId);
    }

    @Override
    @Transactional
    public void createClient(ClientEntity clientEntity) {
        if (!clientRepository.existsByMdmCode(clientEntity.getMdmCode())) {
            clientEntity.setCreationDate(OffsetDateTime.now());
            clientRepository.saveAndFlush(clientEntity);
        } else if (clientRepository.existsByMdmCode(clientEntity.getMdmCode())) {
            throw new ConflictException("Клиент с таким mdmId уже существует");
        } else throw new BadRequestException("Невалидные данные");
    }

    @Override
    @Transactional
    public  ClientEntity getClientById(UUID clientId) {
        if (clientRepository.existsById(clientId)) {
            return clientRepository.findClientById(clientId);
        } else throw new NotFoundException("Клиент не найден");
    }
}
