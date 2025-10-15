package alexgordeeff.ms_upgrade.repository;

import alexgordeeff.ms_upgrade.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountEntity, Integer> {
}
