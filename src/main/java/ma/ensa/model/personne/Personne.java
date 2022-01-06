package ma.ensa.model.personne;

import lombok.Data;
import ma.ensa.model.enumer.IDENTITE;
import ma.ensa.model.enumer.PAYS;
import ma.ensa.model.enumer.TITRE;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public abstract class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private TITRE titre;
    private String prenom;
    private String nom;
    private IDENTITE identite;
    private PAYS pays;
    private Long numIdentite;
    private Date expiration;
    private Date naissance;
    private String profession;
    private PAYS nationalite;
    private String adressePays;
    private String adresseLegale;
    private String ville;
    private Long gsm;
    private String email;




}
