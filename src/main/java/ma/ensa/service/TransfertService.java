package ma.ensa.service;

import ma.ensa.exception.TransfertDuplicatedException;
import ma.ensa.exception.TransfertNotFoundException;
import ma.ensa.model.Transfert;

import java.util.List;

public interface TransfertService {

    Transfert save(Transfert transfert) throws TransfertDuplicatedException;
    Transfert update(Transfert transfert) throws TransfertNotFoundException;//on ne peut pas modifier un transfert; seulement l'extourner pour resaisir un autre
    Long delete(Long ref) throws TransfertNotFoundException;
    List<Transfert> findAll();
    List<Transfert> findAllByClientId(Long clientId);
    List<Transfert> findAllByAgentId(Long clientId);
    List<Transfert> findAllByBeneficiaireId(Long beneficiaireId);
    List<Transfert> findAllByClientBanqueId(Long clientId);

    Transfert findByRef(String ref);
}
