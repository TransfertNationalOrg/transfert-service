package ma.ensa.repository;

import ma.ensa.model.notification.NotificationGAB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationGABRepository extends JpaRepository<NotificationGAB, Long> {
}
