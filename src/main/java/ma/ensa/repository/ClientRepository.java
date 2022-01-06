package ma.ensa.repository;

import ma.ensa.model.personne.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
