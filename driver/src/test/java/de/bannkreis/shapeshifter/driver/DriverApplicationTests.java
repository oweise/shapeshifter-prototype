package de.bannkreis.shapeshifter.driver;

import de.bannkreis.shapeshifter.driver.frontend.entities.JobStartResponse;
import de.bannkreis.shapeshifter.driver.jobengine.JobManager;
import de.bannkreis.shapeshifter.driver.jobengine.RunningJobsManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverApplicationTests {

	@LocalServerPort
	private int port;

	@MockBean
	private RunningJobsManager runningJobsManager;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private JobManager jobManager;

	@Test
	public void contextLoads() {
	}

	@Before
    public void setup() {
	    Job job = new Job();
	    job.setName("thatjob");
	    job.setGitUrlPattern("\\Qhttps://github.com/oweise/shapeshifter-prototype.git\\E");
	    job.setGitRefPattern("\\Qrefs/heads/master\\E");
	    jobManager.addJob(job);
    }

	@Test
	public void shouldScheduleJob() throws URISyntaxException, IOException {

		// WHEN
		String json = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("webhook.json").toURI())));
		HttpHeaders headers = new HttpHeaders();
 		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
		ResponseEntity<JobStartResponse> response = testRestTemplate.postForEntity(
				String.format("http://localhost:%d/webhooks", port),
				httpEntity,
				JobStartResponse.class);

		// THEN
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getNewJobRunIds());
		JobRun expectedJobRun = new JobRun(UUID.randomUUID(), "https://github.com/oweise/shapeshifter-prototype.git",
				"refs/heads/master", "da1560886d4f094c3e6c9ef40349f7d38b5d27d7");
		Mockito.verify(runningJobsManager).addJobRun(Mockito.eq(expectedJobRun));


	}

}
