package de.bannkreis.shapeshifter.driver.frontend;

import de.bannkreis.shapeshifter.driver.frontend.entities.JobRepository;
import de.bannkreis.shapeshifter.driver.frontend.entities.Webhook;
import de.bannkreis.shapeshifter.driver.frontend.ollers.WebhookController;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import de.bannkreis.shapeshifter.driver.jobengine.WebhookProcessor;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

public class WebhookControllerTest {

    @Test
    public void shouldStartJob() {

        // GIVEN
        WebhookProcessor runningJobsManager = Mockito.mock(WebhookProcessor.class);
        Webhook jobStart = new Webhook();
        JobRepository jobRepository = new JobRepository();
        jobRepository.setUrl("http://project.uri");
        jobStart.setRepository(jobRepository);
        jobStart.setRef("refs/heads/master");

        // WHEN
        WebhookController webhookController = new WebhookController(runningJobsManager);
        webhookController.receiveWebhook(jobStart);

        // THEN
        JobRun jobRun = new JobRun(UUID.randomUUID(), "http://project.uri", "refs/heads/master");
        Mockito.verify(runningJobsManager).processWebhook(jobStart);

    }


}