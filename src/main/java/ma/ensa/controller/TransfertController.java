package ma.ensa.controller;

import lombok.Data;
import ma.ensa.service.TransfertService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transfert")
@Data
public class TransfertController {

    final TransfertService transfertService;


}
