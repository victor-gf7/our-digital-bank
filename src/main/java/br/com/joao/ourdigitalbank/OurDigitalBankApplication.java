package br.com.joao.ourdigitalbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.com.joao.ourdigitalbank")
@EntityScan(basePackages = "br.com.joao.ourdigitalbank.model")
public class OurDigitalBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(OurDigitalBankApplication.class, args);
	}
}
