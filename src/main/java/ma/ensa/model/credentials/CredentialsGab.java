package ma.ensa.model.credentials;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class CredentialsGab {
    protected String ref;
    protected int PIN;

}
