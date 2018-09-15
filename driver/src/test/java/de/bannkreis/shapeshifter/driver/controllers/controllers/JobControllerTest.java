package de.bannkreis.shapeshifter.driver.controllers.controllers;

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

        // WHEN
        JobController jobController = new JobController(runningJobsManager);
        jobController.startJob("http://project.uri", "http://library.uri");

        // THEN
        JobRun jobRun = new JobRun("http://project.uri", "http://library.uri");
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