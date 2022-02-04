package ma.ensa.back_office;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "back-office-service")
public interface ParametreFeign {
    //Get parametere from back-office service
    @GetMapping("parametre/")
    List<ParametreDTO> findAll();
}
