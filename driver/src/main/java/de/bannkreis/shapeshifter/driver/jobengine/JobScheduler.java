package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class JobScheduler {

    private final RunningJobsManager runningJobsManager;

    private Executor executor = Executors.newFixedThreadPool(10);

    public JobScheduler(RunningJobsManager runningJobsManager) {
        this.runningJobsManager = runningJobsManager;
    }

    @Scheduled(fixedRate = 10000)
    public void scheduleJobs() {

        for (UUID jobId : runningJobsManager.getRunningJobIds()) {
            JobRun jobRun = runningJobsManager.getJobRun(jobId);
        }

    }
}
