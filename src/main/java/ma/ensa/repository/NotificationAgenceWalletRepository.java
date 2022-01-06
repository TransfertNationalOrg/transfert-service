package ma.ensa.repository;

import ma.ensa.model.notification.NotificationAgenceWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationAgenceWalletRepository extends JpaRepository<NotificationAgenceWallet, Long> {
}
