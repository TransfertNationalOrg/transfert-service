package ma.ensa.model;

import lombok.Data;
import ma.ensa.agent.AgentDTO;
import ma.ensa.beneficiaire.BeneficiaireDTO;
import ma.ensa.client.ClientDTO;

import javax.persistence.*;

@Data
@Entity
public class Transfert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    protected Long ref;
    protected double commission;
    protected String motif;
    protected AgentDTO agent;
    protected ClientDTO clientt;
    protected BeneficiaireDTO beneficiaire;


}
