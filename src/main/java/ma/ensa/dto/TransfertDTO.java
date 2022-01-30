package ma.ensa.dto;

import lombok.Data;
import ma.ensa.model.enumer.ETAT;

@Data
public class TransfertDTO {
    protected Long id;
    protected double montant;
    protected String ref;
    protected double commission;
    protected String motif;
    protected Long idEmetteur;
    protected Long idClient;
    protected Long idBeneficiaire;
    protected ETAT etat = ETAT.A_SERVIR;
    protected Long idEmetteurServ;
    protected int PIN;
}
