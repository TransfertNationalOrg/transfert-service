package ma.ensa.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NotificationBOA extends NotificationAgenceWallet {
        private int PIN;
}
