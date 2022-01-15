package ma.ensa.repository;

import ma.ensa.model.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TransfertRepository extends JpaRepository<Transfert, Long> {

    @Query("SELECT transfert from Transfert transfert where transfert.idClient=:x")
    List<Transfert> findByClientId(@Param("x") Long clientId);

    @Query("SELECT transfert from Transfert transfert where transfert.idAgent=:x")
    List<Transfert> findByAgentId(@Param("x") Long agentId);

    @Query("SELECT transfert from Transfert transfert where transfert.idBeneficiaire=:x")
    List<Transfert> findByBeneficiaireId(@Param("x") Long beneficiaireId);

    @Query("SELECT transfert from Transfert transfert where transfert.idClient=:x")
    List<Transfert> findByClientBanqueId(@Param("x") Long clientId);
}
