package ma.ensa.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NotificationAgenceWallet extends Notification {
    private double montant;

}
