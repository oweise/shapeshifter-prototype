package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobScheduler {

    private Logger LOG = LoggerFactory.getLogger(JobScheduler.class);

    private final JobRunManager jobRunManager;

    private final JobRunProcessor jobRunProcessor;

    private Executor executor = Executors.newFixedThreadPool(10);

    @Autowired
    public JobScheduler(JobRunManager jobRunManager, JobRunProcessor jobRunProcessor) {
        this.jobRunManager = jobRunManager;
        this.jobRunProcessor = jobRunProcessor;
    }

    @Scheduled(fixedRate = 10000)
    public void scheduleJobs() throws IOException, GitAPIException {

        for (UUID jobId : jobRunManager.getRunningJobIds()) {
            jobRunManager.getJobRun(jobId).ifPresent((jobRun)-> {
                try {
                    jobRunProcessor.processJobRun(jobRun);
                }
                catch (Exception e) {
                    LOG.error("Exception scheduling job", e);
                }
            });
        }

    }
}
