package ma.ensa.controller;

import lombok.Data;
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


}
