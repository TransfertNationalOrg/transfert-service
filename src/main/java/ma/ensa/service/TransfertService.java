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
}
