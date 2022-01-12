package ma.ensa.cmi;

import ma.ensa.client.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="transfert-service")

public interface ClientBanqueFeign {
    //Get all clients from cmi-service
    @GetMapping("clientBanque/")
    List<ClientBanqueDTO> findAll();

    //Get a client by id from cmi-service
    @GetMapping("clientBanque/{idClientBanque}")
    ClientBanqueDTO getClientBanqueById(@PathVariable("idClientBanque") Long id);
}
