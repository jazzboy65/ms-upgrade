package alexgordeeff.ms_upgrade.repository;

import alexgordeeff.ms_upgrade.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    boolean existsByMdmCode(Long mdmCode);

}
