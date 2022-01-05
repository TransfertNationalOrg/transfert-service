package ma.ensa.model;

import lombok.Data;
import ma.ensa.model.personne.Agent;
import ma.ensa.model.personne.Personne;

import javax.persistence.*;

@Data
@Entity
public class Transfert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long ref;
    private double commission;
    private ETAT etat;
    private boolean notification;//Si on l'émetteur souhaite recevoir un accusé de réception
    @ManyToOne
    @JoinColumn(name = "beneficiaire_ID")
    private Personne beneficiaire;
    @ManyToOne
    @JoinColumn(name = "emetteur_ID")
    private Personne emetteur;
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
    private MODE emission;
    private MODE reception;
    private String motif;





}
