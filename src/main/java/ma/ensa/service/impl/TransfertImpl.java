package ma.ensa.service.impl;

import lombok.Data;
import ma.ensa.exception.TransfertDuplicatedException;
import ma.ensa.exception.TransfertNotFoundException;
import ma.ensa.model.Transfert;
import ma.ensa.model.enumer.ETAT;
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
        Long id = transfertRepository.save(transfert).getId();

        //Manage the reference by updating
        String ref = "EDP837" + generateRef(id);
        transfert.setRef(ref);
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

    @Override
    public List<Transfert> findAllByClientId(Long clientId) {
        return transfertRepository.findByClientId(clientId);
    }

    @Override
    public List<Transfert> findAllByAgentId(Long agentId) {
        return transfertRepository.findByAgentId(agentId);
    }

    @Override
    public List<Transfert> findAllByBeneficiaireId(Long beneficiaireId) {
        return transfertRepository.findByBeneficiaireId(beneficiaireId);
    }

    @Override
    public List<Transfert> findAllByClientBanqueId(Long clientId) {
        return transfertRepository.findByClientBanqueId(clientId);
    }

    @Override
    public Transfert findByRef(String ref) {
        return transfertRepository.findByRef(ref);
    }

    private String generateRef (Long id){
        return String.format("%013d",id+1);
    }

    public Boolean verifMontantClient(double montant) {

        if(montant > 2000 ) {
            return false;

        }
        else {
            return true;
        }
    }

    public Boolean verifMontantAgence(double montant) {

        if(montant > 80000) {
            return false;

        }
        else {
            return true;
        }
    }

    public ETAT getEtatTransfert(double montant, boolean otp) {
        ETAT etat;

        if(verifMontantClient(montant) == false || otp == false || verifMontantAgence(montant) == false ) {
            etat = ETAT.EXTOURNE;
        }
        else {
            etat= ETAT.A_SERVIR;
        }
        return etat;
    }
}
