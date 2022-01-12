package ma.ensa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransfertServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransfertServiceApplication.class, args);
	}

}
