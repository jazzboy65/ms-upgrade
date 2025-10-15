package alexgordeeff.ms_upgrade.service;

import java.util.UUID;

public interface MsUpgradeService {

    boolean checkClientExists( UUID clientId);
}
