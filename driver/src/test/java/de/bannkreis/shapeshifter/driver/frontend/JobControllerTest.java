package de.bannkreis.shapeshifter.driver.frontend;

import de.bannkreis.shapeshifter.driver.frontend.controllers.JobController;
import de.bannkreis.shapeshifter.driver.frontend.entities.JobRepository;
import de.bannkreis.shapeshifter.driver.frontend.entities.JobStartRequest;
import de.bannkreis.shapeshifter.driver.jobengine.RunningJobsManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class JobControllerTest {

    @Test
    public void shouldStartJob() {

        // GIVEN
        RunningJobsManager runningJobsManager = Mockito.mock(RunningJobsManager.class);
        JobStartRequest jobStart = new JobStartRequest();
        JobRepository jobRepository = new JobRepository();
        jobRepository.setUrl("http://project.uri");
        jobStart.setRepository(jobRepository);

        // WHEN
        JobController jobController = new JobController(runningJobsManager);
        jobController.startJob(jobStart);

        // THEN
        JobRun jobRun = new JobRun("http://project.uri");
        Mockito.verify(runningJobsManager).addJobRun(Mockito.eq(jobRun));

    }

    @Test
    public void shouldListJobs() {

        // GIVEN
        Set<UUID> theUUIDs = Stream.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
                .collect(Collectors.toSet());
        RunningJobsManager runningJobsManager = Mockito.mock(RunningJobsManager.class);
        Mockito.when(runningJobsManager.getRunningJobIds()).thenReturn(theUUIDs);

        // WHEN
        JobController jobController = new JobController(runningJobsManager);
        Set<String> ids = jobController.getRunningJobIds();

        // THEN
        assertEquals(theUUIDs.stream().map(UUID::toString).collect(Collectors.toSet()), ids);


    }

}