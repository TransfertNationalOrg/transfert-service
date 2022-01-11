package ma.ensa.cmi;

import lombok.Data;

import java.util.Date;

@Data
public class CompteBancaireDTO {
    protected Long id;
    protected Long RIB;
    protected double solde;
    protected Date date;
    protected Long idClient;
}
