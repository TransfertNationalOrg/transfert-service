package ma.ensa.controller;

import lombok.Data;
import ma.ensa.converter.TransfertConverter;
import ma.ensa.dto.TransfertDTO;
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

    final TransfertService transfertService;
    final TransfertRepository transfertRepository;
    final TransfertConverter transfertConverter;

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

    @GetMapping("/")
    public ResponseEntity<List<TransfertDTO>> findAll() {
        return ResponseEntity.ok().body(transfertConverter.convertToDTOs(transfertService.findAll()));
    }

    @GetMapping("/transfert/{idClient}")
    List<Transfert> getTransfertsByClient(@PathVariable("idClient") Long idClient){
        return transfertService.findAllByClientId(idClient);
    }

    @GetMapping("/transfert/{idAgent}")
    List<Transfert> getTransfertsByAgent(@PathVariable("idAgent") Long idAgent){
        return transfertService.findAllByClientId(idAgent);
    }


}
