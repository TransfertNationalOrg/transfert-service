package ma.ensa.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Transfert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long ref;
    private double commission;
    private boolean notification;//Si on l'émetteur souhaite recevoir un accusé de réception
    private String motif;


}
