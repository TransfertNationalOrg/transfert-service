package ma.ensa.cmi;

import ma.ensa.client.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="transfert-service")

public interface CompteBancaireFeign {
    //Get all compteBancaire from cmi-service
    @GetMapping("compteBancaire/")
    List<ClientDTO> findAll();

    //Get a compteBancaire by id from cmi-service
    @GetMapping("compteBancaire/{idCompteBancaire}")
    CompteBancaireDTO getCompteBancaireById(@PathVariable("idCompteBancaire") Long id);
}
