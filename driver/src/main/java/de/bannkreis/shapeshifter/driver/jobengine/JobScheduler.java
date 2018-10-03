package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobScheduler {

    private final RunningJobsManager runningJobsManager;

    private final JobRunProcessor jobRunProcessor;

    private Executor executor = Executors.newFixedThreadPool(10);

    @Autowired
    public JobScheduler(RunningJobsManager runningJobsManager, JobRunProcessor jobRunProcessor) {
        this.runningJobsManager = runningJobsManager;
        this.jobRunProcessor = jobRunProcessor;
    }

    @Scheduled(fixedRate = 10000)
    public void scheduleJobs() {

        for (UUID jobId : runningJobsManager.getRunningJobIds()) {
            JobRun jobRun = runningJobsManager.getJobRun(jobId);
            jobRunProcessor.processJobRun(jobRun);
        }

    }
}
