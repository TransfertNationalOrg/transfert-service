package ma.ensa.model.notification;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NotificationGAB extends NotificationAgenceWallet {
        private int PIN;
}
