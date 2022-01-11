package ma.ensa.beneficiaire;

import ma.ensa.agent.enumer.PAYS;
import ma.ensa.agent.enumer.TITRE;

public class BeneficiaireDTO {
    protected Long id;

    protected TITRE titre;
    protected String prenom;
    protected String nom;
    protected PAYS pays;
}
