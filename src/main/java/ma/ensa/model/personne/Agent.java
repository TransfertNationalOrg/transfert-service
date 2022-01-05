package ma.ensa.model.personne;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Agent extends Personne {
    private String matricule;
}
