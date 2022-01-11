package ma.ensa.repository;

import ma.ensa.model.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransfertRepository extends JpaRepository<Transfert, Long> {

    @Query("SELECT t from Transfert t where c.getIdClient=:x")
    List<Transfert> findByClientId(@Param("x") Long clientId);

    @Query("SELECT t from Transfert t where c.getIdAgent=:x")
    List<Transfert> findByAgentId(@Param("x") Long agentId);

    @Query("SELECT t from Transfert t where c.getIdBeneficiaire=:x")
    List<Transfert> findByBeneficiaireId(@Param("x") Long beneficiaireId);
}
