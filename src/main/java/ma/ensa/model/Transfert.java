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
    private String motif;


}
