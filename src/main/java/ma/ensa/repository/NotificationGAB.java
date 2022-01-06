package ma.ensa.repository;

import ma.ensa.model.notification.NotificationGAB;
import org.springframework.data.jpa.repository.JpaRepository;

interface NotificationGABRepository extends JpaRepository<NotificationGAB, Long> {
}
