package ma.ensa.repository;

import ma.ensa.model.personne.ClientProspect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProspectRepository extends JpaRepository<ClientProspect, Long> {
}
