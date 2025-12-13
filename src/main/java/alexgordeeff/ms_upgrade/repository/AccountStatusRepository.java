package alexgordeeff.ms_upgrade.repository;

import alexgordeeff.ms_upgrade.model.AccountStatusEntity;
import alexgordeeff.ms_upgrade.model.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatusEntity, Integer> {

    AccountStatusEntity findByName(ClientStatus name);
}
