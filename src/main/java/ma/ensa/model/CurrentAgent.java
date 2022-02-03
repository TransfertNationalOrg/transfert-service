package ma.ensa.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class CurrentAgent {
    @Id
    @Column(name = "id", nullable = false)
    protected Long id;

    protected Long theId;

}
