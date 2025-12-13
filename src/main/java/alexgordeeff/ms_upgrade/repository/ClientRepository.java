package alexgordeeff.ms_upgrade.repository;

import alexgordeeff.ms_upgrade.model.ClientEntity;
import clients.model.ClientWithPageInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    boolean existsByMdmCode(Long mdmCode);

    ClientEntity findClientById(UUID clientId);

    List<ClientWithPageInfo> findAllByMdmCode(Long mdmCode, Pageable pageable);
}
