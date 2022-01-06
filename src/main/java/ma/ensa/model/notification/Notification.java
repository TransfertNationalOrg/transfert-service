package ma.ensa.model.notification;

import lombok.Data;
import ma.ensa.model.Transfert;

import javax.persistence.*;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transfert_id")
    private Transfert transfert;
    private String message;
}
