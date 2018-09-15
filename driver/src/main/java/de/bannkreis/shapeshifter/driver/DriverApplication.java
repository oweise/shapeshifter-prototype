package de.bannkreis.shapeshifter.driver;

import de.bannkreis.shapeshifter.driver.jobengine.JobScheduler;
import de.bannkreis.shapeshifter.driver.jobengine.RunningJobsManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverApplication.class, args);
	}

	@Bean
	public RunningJobsManager runningJobsManager() {
		return new RunningJobsManager();
	}

	@Bean
	public JobScheduler jobScheduler(RunningJobsManager runningJobsManager) {
		return new JobScheduler(runningJobsManager);
	}
}
