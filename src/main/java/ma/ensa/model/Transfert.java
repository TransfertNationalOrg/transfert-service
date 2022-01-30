package ma.ensa.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Transfert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    protected String ref;
    protected double montant;
    protected double commission;
    protected String motif;
    protected Long idAgent;
    protected Long idClient;
    protected Long idBeneficiaire;
    protected ETAT etat = ETAT.A_SERVIR;
}
