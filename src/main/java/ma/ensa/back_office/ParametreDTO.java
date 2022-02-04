package ma.ensa.back_office;

import lombok.Data;

import java.util.Date;

@Data
public class ParametreDTO {

    protected Long id;
    protected double maxSomAgence=20000;
    protected double maxSomWallet=2000;
    protected double maxRetraitGAB=2000;
    protected int maxnbrTranfAgence=50;
    protected int delaiExpiration=15;
    protected int delaiDesherence=10;
    protected double commision=0.2;
    protected double commissionNotification=5;
    protected Date heureBatchBlocage;
    protected Date heureBatchDesherence;
}
