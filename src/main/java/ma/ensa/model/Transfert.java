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
    private Long id;

    private Long ref;
    private double commission;
    private String motif;
    private AgentDTO agent;
    private ClientDTO clientt;
    private BeneficiaireDTO beneficiaire;


}
