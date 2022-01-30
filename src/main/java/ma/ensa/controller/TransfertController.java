package ma.ensa.controller;

import lombok.Data;
import ma.ensa.agent.AgentDTO;
import ma.ensa.agent.AgentFeign;
import ma.ensa.converter.TransfertConverter;
import ma.ensa.dto.TransfertDTO;
import ma.ensa.model.ETAT;
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

    //CRUD

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody TransfertDTO transfertDTO) throws Exception {
        if (transfertDTO == null)
            return ResponseEntity.badRequest().body("The provided transfert is not valid");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transfertConverter.convertToDTO(transfertService.save(transfertConverter.convertToDM(transfertDTO))));
    }

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

    @GetMapping("/agent/{idAgent}")
    List<TransfertDTO> getTransfertsByAgent(@PathVariable("idAgent") Long idAgent){
        return transfertConverter.convertToDTOs(transfertRepository.findByAgentId(idAgent));
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
        AgentDTO agentDTO = agentFeign.getAgentById(transfert.getIdAgent());
        agentDTO.setSolde(agentDTO.getSolde()-transfert.getMontant());
        agentFeign.update(agentDTO);
        return ResponseEntity
                .ok().body(transfertConverter.convertToDTO(transfertService.update(transfert)));
    }


}
