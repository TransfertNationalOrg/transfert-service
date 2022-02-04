package ma.ensa.client;

import ma.ensa.dto.CurrentClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="client-service")
public interface ClientFeign {
    //Get all clients from client-service
    @GetMapping("client/")
    List<ClientDTO> findAll();

    //Get a client by id from client-service
    @GetMapping("client/{id}")
    ClientDTO getClientById(@PathVariable("id") Long id);

    //Update a client
    @PutMapping("client/")
    ClientDTO update(@RequestBody ClientDTO clientDTO);

    //Get current client
    @GetMapping("/")
    CurrentClientDTO getCurrentCllient();
}
