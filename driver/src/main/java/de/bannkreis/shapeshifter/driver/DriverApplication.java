package de.bannkreis.shapeshifter.driver;

import de.bannkreis.shapeshifter.driver.paas.PaasFacade;
import de.bannkreis.shapeshifter.driver.paas.openshift.OpenShiftFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverApplication.class, args);
	}

	@Bean
	@Lazy(true)
	public PaasFacade paasFacade(OpenShiftFacade openShiftFacade) {
		return openShiftFacade;
	}
}
