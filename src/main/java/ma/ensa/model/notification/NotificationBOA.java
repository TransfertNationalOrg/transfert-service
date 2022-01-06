package ma.ensa.model.notification;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NotificationBOA extends NotificationAgenceWallet {
        private int PIN;
}
