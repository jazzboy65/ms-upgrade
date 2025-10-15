package alexgordeeff.ms_upgrade.service.impl;

import alexgordeeff.ms_upgrade.exception.NotFoundException;
import alexgordeeff.ms_upgrade.repository.AccountRepository;
import alexgordeeff.ms_upgrade.service.MsUpgradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MsUpgradeServiceImpl implements MsUpgradeService {

    private final AccountRepository accountRepository;

    @Override
    public boolean checkClientExists(UUID clientId) {
        var exists = accountRepository.existsById(clientId);
        if (!exists) {
            throw new NotFoundException("Клиент не найден");
        }
        return true;
    }
}
