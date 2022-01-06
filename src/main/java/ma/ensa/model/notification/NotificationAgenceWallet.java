package ma.ensa.model.notification;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NotificationAgenceWallet extends Notification {
    private double montant;

}
