package ma.ensa.service.impl;

import lombok.Data;
import ma.ensa.exception.TransfertDuplicatedException;
import ma.ensa.exception.TransfertNotFoundException;
import ma.ensa.model.Transfert;
import ma.ensa.repository.TransfertRepository;
import ma.ensa.service.TransfertService;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class TransfertImpl implements TransfertService {

    final TransfertRepository transfertRepository;

    @Override
    public Transfert save(Transfert transfert) throws TransfertDuplicatedException {
        Transfert transfertFromDB = transfertRepository.findById(transfert.getId()).orElse(null);
        if (transfertFromDB != null)
            throw new TransfertDuplicatedException(transfert.getId());
        return transfertRepository.save(transfert);
    }

    @Override
    public Transfert update(Transfert transfert) throws TransfertNotFoundException {
        Transfert transfertFromDB = transfertRepository.findById(transfert.getId()).orElse(null);
        if (transfertFromDB == null)
            throw new TransfertNotFoundException(transfert.getId());
        transfert.setId(transfertFromDB.getId());
        return transfertRepository.save(transfert);
    }

    @Override
    public Long delete(Long ref) throws TransfertNotFoundException {
        Transfert transfertFromDB = transfertRepository.findById(ref).orElse(null);
        if (transfertFromDB == null)
            throw new TransfertNotFoundException(ref);
        transfertRepository.delete(transfertFromDB);
        return ref;
    }

    @Override
    public List<Transfert> findAll() {
        return transfertRepository.findAll();
    }
}
