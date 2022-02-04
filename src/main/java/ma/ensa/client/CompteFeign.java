package ma.ensa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "client-service")
public interface CompteFeign {
    //Get all comptes from client-service
    @GetMapping("compte/")
    List<CompteDTO> findall();

    //Get a compte by id
    @GetMapping("compte/{id}")
    CompteDTO getCompteById(@PathVariable("id") Long id);

    //Update a compte
    @PutMapping("compte/")
    CompteDTO update(@RequestBody CompteDTO compteDTO);

}
