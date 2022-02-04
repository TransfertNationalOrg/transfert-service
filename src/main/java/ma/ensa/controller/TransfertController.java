package ma.ensa.controller;

import lombok.Data;
import ma.ensa.agent.AgentDTO;
import ma.ensa.agent.AgentFeign;
import ma.ensa.back_office.ParametreDTO;
import ma.ensa.back_office.ParametreFeign;
import ma.ensa.beneficiaire.BeneficiaireDTO;
import ma.ensa.beneficiaire.BeneficiaireFeign;
import ma.ensa.client.ClientDTO;
import ma.ensa.client.ClientFeign;
import ma.ensa.cmi.ClientBanqueDTO;
import ma.ensa.cmi.ClientBanqueFeign;
import ma.ensa.cmi.CompteBancaireDTO;
import ma.ensa.cmi.CompteBancaireFeign;
import ma.ensa.converter.TransfertConverter;
import ma.ensa.dto.CurrentAgentDTO;
import ma.ensa.dto.CurrentClientDTO;
import ma.ensa.dto.TransfertDTO;
import ma.ensa.model.credentials.CredentialsGab;
import ma.ensa.model.enumer.ETAT;
import ma.ensa.model.Transfert;
import ma.ensa.repository.TransfertRepository;
import ma.ensa.service.TransfertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transfert")
@Data
public class TransfertController {

    private final TransfertService transfertService;
    private final TransfertRepository transfertRepository;
    private final TransfertConverter transfertConverter;

    private final AgentFeign agentFeign;
    private final ClientFeign clientFeign;
    private final ClientBanqueFeign clientBanqueFeign;
    private final CompteBancaireFeign compteBancaireFeign;
    private final ParametreFeign parametreFeign;



    //CRUD
    //Ajouter un transfert via console-agent &back-office & wallet
    //en espèces
    @PostMapping("/agent/especes")
    public ResponseEntity<?> save(@RequestBody TransfertDTO transfertDTO) throws Exception {
        if (transfertDTO == null)
            return ResponseEntity.badRequest().body("The provided transfert is not valid");
        CurrentAgentDTO currentAgentDTO = agentFeign.getCurrentAgent();
        //--> Id de l'agent courant à idEmetteur
        transfertDTO.setIdEmetteur(currentAgentDTO.getTheId());
        //gestion de la commission
        ParametreDTO parametreDTO = parametreFeign.findAll().get(0);
        double pourcentageCommission = parametreDTO.getCommision();
        double commission = transfertDTO.getMontant()*pourcentageCommission;
        transfertDTO.setCommission(commission);
        //On met à jour le solde
        AgentDTO agentDTO = agentFeign.getAgentById(currentAgentDTO.getTheId());
        //On met à jour le solde de l'agent
        agentDTO.setSolde(agentDTO.getSolde()+transfertDTO.getMontant()+commission);
        agentFeign.update(agentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transfertConverter.convertToDTO(transfertService.save(transfertConverter.convertToDM(transfertDTO))));
    }

    //Ajouter un transfert par débit compte bancaire
    @PostMapping("/agent/cmi")
    public ResponseEntity<?> saveViaCmi(@RequestBody TransfertDTO transfertDTO) throws Exception {
        if (transfertDTO == null)
            return ResponseEntity.badRequest().body("The provided transfert is not valid");
        ClientBanqueDTO clientBanqueDTO = clientBanqueFeign.getClientBanqueById(transfertDTO.getIdClient());
        CompteBancaireDTO compteBancaireDTO = compteBancaireFeign.getCompteBancaireById(clientBanqueDTO.getIdCompteBancaire());
        CurrentAgentDTO currentAgentDTO = agentFeign.getCurrentAgent();
        //VERIFIER SI LE CLIENT DISPOSE ASSEZ DE SOLDE DANS SON COMPTE BANCAIRE POUR LE TRANSFERT
        if (compteBancaireDTO.getSolde()<transfertDTO.getMontant()){
            return ResponseEntity.badRequest().body("The client has not enough money");
        }
        //--> Id de l'agent courant à idEmetteur
        transfertDTO.setIdEmetteur(currentAgentDTO.getTheId());
        //gestion de la commission
        ParametreDTO parametreDTO = parametreFeign.findAll().get(0);
        double pourcentageCommission = parametreDTO.getCommision();
        double commission = transfertDTO.getMontant()*pourcentageCommission;
        transfertDTO.setCommission(commission);
        //
        compteBancaireDTO.setSolde(compteBancaireDTO.getSolde()-transfertDTO.getMontant()-commission);
        compteBancaireFeign.update(compteBancaireDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transfertConverter.convertToDTO(transfertService.save(transfertConverter.convertToDM(transfertDTO))));
    }

    //Ajouter un transfert via wallet
    @PostMapping("/wallet")
    public ResponseEntity<?> saveFromWallet(@RequestBody TransfertDTO transfertDTO) throws Exception {
        if (transfertDTO == null)
            return ResponseEntity.badRequest().body("The provided transfert is not valid");
        CurrentClientDTO currentClientDTO = clientFeign.getCurrentCllient();
        //VERIFIER SI LE CLIENT DISPOSE ASSEZ DE SOLDE POUR LE TRANSFERT
        transfertDTO.setIdEmetteur(currentClientDTO.getTheId());
        //On met à jour le solde
        ClientDTO clientDTO = clientFeign.getClientById(currentClientDTO.getTheId());
        clientDTO.setSolde(clientDTO.getSolde()-transfertDTO.getMontant());
        clientFeign.update(clientDTO);
        //Bénéficiaire est client
        ClientDTO clientBeneficiaireDTO = clientFeign.getClientById(transfertDTO.getIdBeneficiaire());
        clientBeneficiaireDTO.setSolde(clientBeneficiaireDTO.getSolde()+transfertDTO.getMontant());
        clientFeign.update(clientBeneficiaireDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transfertConverter.convertToDTO(transfertService.save(transfertConverter.convertToDM(transfertDTO))));
    }
    //Gerer les commissions

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody TransfertDTO transfertDTO) throws Exception {
        if (transfertDTO == null)
            return ResponseEntity.badRequest().body("The provided transfert is not valid");
        return ResponseEntity
                .ok().body(transfertConverter.convertToDTO(transfertService.update(transfertConverter.convertToDM(transfertDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        if(id == null)
            return ResponseEntity.badRequest().body("The provided transfert's id is not valid");
        return ResponseEntity.ok().body("Transfert [" + transfertService.delete(id) + "] deleted successfully.");
    }

    //Get Transfert by id
    @GetMapping("/{id}")
    public TransfertDTO getTransfertById(@PathVariable("id") Long id){
        return transfertConverter.convertToDTO(transfertRepository.getById(id));
    }


    @GetMapping("/")
    public ResponseEntity<List<TransfertDTO>> findAll() {
        return ResponseEntity.ok().body(transfertConverter.convertToDTOs(transfertService.findAll()));
    }

    @GetMapping("/client/{idClient}")
    List<TransfertDTO> getTransfertsByClient(@PathVariable("idClient") Long idClient){
        return transfertConverter.convertToDTOs(transfertService.findAllByClientId(idClient));
    }

    @GetMapping("/agent/{idEmetteur}")
    List<TransfertDTO> getTransfertsByAgent(@PathVariable("idEmetteur") Long idEmetteur){
        return transfertConverter.convertToDTOs(transfertRepository.findByAgentId(idEmetteur));
    }

    @GetMapping("/beneficiaire/{idBeneficiaire}")
    List<TransfertDTO> getTransfertsByBeneficiaire(@PathVariable("idBeneficiaire") Long idBeneficiaire){
        return transfertConverter.convertToDTOs(transfertService.findAllByBeneficiaireId(idBeneficiaire));
    }

    //Bloquer un transfert
    @PutMapping("/bloquer/{id}")
    public ResponseEntity<?> bloquer(@PathVariable Long id) throws Exception {
        if (id == null || transfertRepository.getById(id) == null)
            return ResponseEntity.badRequest().body("The provided id is not valid");
        Transfert transfert = transfertRepository.getById(id);
        //On peut bloquer un transfert que s'il est à l'état A_SERVIR
        if (!transfert.getEtat().equals(ETAT.A_SERVIR))
            return ResponseEntity.badRequest().body("This operation is not permitted");
        transfert.setEtat(ETAT.BLOQUE);
        return ResponseEntity
                .ok().body(transfertConverter.convertToDTO(transfertService.update(transfert)));
    }

    //Débloquer un transfert
    @PutMapping("/debloquer/{id}")
    public ResponseEntity<?> debloquer(@PathVariable Long id) throws Exception {
        if (id == null || transfertRepository.getById(id) == null)
            return ResponseEntity.badRequest().body("The provided is not valid");
        Transfert transfert = transfertRepository.getById(id);
        //On ne peut débloquer un transfert que s'il est à l'état BLOQUE
        if (!transfert.getEtat().equals(ETAT.BLOQUE))
            return ResponseEntity.badRequest().body("This operation is not permitted");
        transfert.setEtat(ETAT.A_SERVIR);
        return ResponseEntity
                .ok().body(transfertConverter.convertToDTO(transfertService.update(transfert)));
    }

    //Restituer un transfert
    @PutMapping("/restituer/{id}")
    public ResponseEntity<?> restituer(@PathVariable Long id) throws Exception {
        if (id == null || transfertRepository.getById(id) == null)
            return ResponseEntity.badRequest().body("The provided id is not valid");
        Transfert transfert = transfertRepository.getById(id);
        //On ne peut restituer un transfert que dans les Etats A_SERVIR & DEBLOQUE_A_SERVIR
        if (!(transfert.getEtat().equals(ETAT.A_SERVIR) || transfert.getEtat().equals(ETAT.DEBLOQUE_A_SERVIR)))
            return ResponseEntity.badRequest().body("This operation is not permitted");
        transfert.setEtat(ETAT.RESTITUE);
        //On met à jour le solde de l'agent
        AgentDTO agentDTO = agentFeign.getAgentById(transfert.getIdEmetteur());
        agentDTO.setSolde(agentDTO.getSolde()-transfert.getMontant());
        agentFeign.update(agentDTO);
        //Vérifier les conditions de remboursement de la commission
        return ResponseEntity
                .ok().body(transfertConverter.convertToDTO(transfertService.update(transfert)));
    }

    //Extourner un transfert
    @PutMapping("/extourner/{id}")
    public ResponseEntity<?> extourner(@PathVariable Long id) throws Exception {
        if (id == null || transfertRepository.getById(id) == null)
            return ResponseEntity.badRequest().body("The provided id is not valid");
        Transfert transfert = transfertRepository.getById(id);
        //On ne peut extourner un transfert que s'il est dans les etats : A_SERVIR, BLOQUE, DEBLOQUE_A_SERVIR
        if (!(transfert.getEtat().equals(ETAT.A_SERVIR) || transfert.getEtat().equals(ETAT.BLOQUE) || transfert.equals(ETAT.DEBLOQUE_A_SERVIR)))
            return ResponseEntity.badRequest().body("This operation is not permitted");
        transfert.setEtat(ETAT.EXTOURNE);
        //On met à jour le solde de l'agent
        //--> transfert.setIdEmetteur(IdCourant);
        AgentDTO agentDTO = agentFeign.getAgentById(transfert.getIdEmetteur());
        agentDTO.setSolde(agentDTO.getSolde()-transfert.getMontant());
        agentFeign.update(agentDTO);
        //Vérifier les conditions de remboursement de la commission
        return ResponseEntity
                .ok().body(transfertConverter.convertToDTO(transfertService.update(transfert)));
    }

    //SERVIR UN TRANSFERT

    //Sur le frontend, on fait une recherche par référence pour afficher le nom du bénéficiaire; après on clique sur payer pour appeler la
    //méthode suivante
    @PutMapping("/servir/agent")
    public ResponseEntity<?> servir(@RequestBody String ref) throws Exception {
        Transfert transfert = transfertService.findByRef(ref);
        if (transfert == null)
            return ResponseEntity.badRequest().body("The provided reference is not valid");
        transfert.setEtat(ETAT.SERVI);
        //-->mettre à jour l'idEmetteurServ par l'id de l'agent courant
        //--> transfert.setIdEmetteur(IdCourant);
        //On met à jour le solde de l'agent
        AgentDTO agentDTO = agentFeign.getAgentById(transfert.getIdEmetteur());
        agentDTO.setSolde(agentDTO.getSolde()-transfert.getMontant());
        agentFeign.update(agentDTO);
        return ResponseEntity
                .ok().body(transfertConverter.convertToDTO(transfertService.update(transfert)));
    }

    @PutMapping("/servir/gab")
    public ResponseEntity<?> servir(@RequestBody CredentialsGab gab) throws Exception {
        Transfert transfert = transfertService.findByRef(gab.getRef());
        if (transfert == null)
            return ResponseEntity.badRequest().body("The provided reference is not valid");
        if (transfert.getPIN()!= gab.getPIN())
            return ResponseEntity.badRequest().body("The provided PIN is not valid");
        return ResponseEntity
                .ok().body(transfertConverter.convertToDTO(transfertService.update(transfert)));
    }


}
