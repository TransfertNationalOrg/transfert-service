package ma.ensa.cmi;

import ma.ensa.client.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="transfert-service")

public interface CompteBancaireFeign {
    //Get all compteBancaire from cmi-service
    @GetMapping("compteBancaire/")
    List<ClientDTO> findAll();

    //Get a compteBancaire by id from cmi-service
    @GetMapping("compteBancaire/{idCompteBancaire}")
    CompteBancaireDTO getCompteBancaireById(@PathVariable("idCompteBancaire") Long idCompteBancaire);

    //Get a compteBancaire by idClient from cmi-service
    @GetMapping("compteBancaire/{idClient")
    CompteBancaireDTO getCompteBancaireByIdClient(@PathVariable("idClient") Long idClient);

    //Update a compteBancaire
    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody CompteBancaireDTO compteBancaireDTO);
}
