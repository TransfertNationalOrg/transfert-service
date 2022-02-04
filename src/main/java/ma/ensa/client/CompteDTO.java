package ma.ensa.client;

import lombok.Data;

import java.util.Date;

@Data
public class CompteDTO {

    protected int id;
    protected double solde;
    protected Date date;
    protected Long idClient;

}
