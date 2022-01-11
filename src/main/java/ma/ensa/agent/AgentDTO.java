package ma.ensa.agent;

import lombok.Data;
import ma.ensa.agent.enumer.IDENTITE;
import ma.ensa.agent.enumer.PAYS;
import ma.ensa.agent.enumer.ROLE;
import ma.ensa.agent.enumer.TITRE;

import java.util.Date;

@Data
public class AgentDTO {
    protected Long id;
    protected TITRE titre;
    protected String prenom;
    protected String nom;
    protected IDENTITE identite;
    protected PAYS pays;
    protected Long numIdentite;
    protected Date expiration;
    protected Date naissance;
    protected String profession;
    protected PAYS nationalite;
    protected String adressePays;
    protected String adresseLegale;
    protected String ville;
    protected Long gsm;
    protected String email;
    protected String matricule;
    protected ROLE role;
}
