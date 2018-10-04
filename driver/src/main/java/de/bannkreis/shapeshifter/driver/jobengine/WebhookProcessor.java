package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.frontend.entities.Webhook;
import de.bannkreis.shapeshifter.driver.jobengine.JobManager;
import de.bannkreis.shapeshifter.driver.jobengine.RunningJobsManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class WebhookProcessor {

    private final RunningJobsManager runningJobsManager;
    private final JobManager jobManager;

    public WebhookProcessor(JobManager jobManager, RunningJobsManager runningJobsManager) {
        this.jobManager = jobManager;
        this.runningJobsManager = runningJobsManager;
    }

    public List<UUID> processWebhook(Webhook webhook) {

        List<UUID> newJobRuns = new ArrayList<>();
        for (UUID jobId : jobManager.getJobIds()) {
            Job job = jobManager.getJob(jobId);

            Pattern gitUrlPattern = Pattern.compile(job.getGitUrlPattern());
            if (!gitUrlPattern.matcher(webhook.getRepository().getUrl()).matches()) {
                continue;
            }

            Pattern gitRefPattern = Pattern.compile(job.getGitRefPattern());
            if (!gitRefPattern.matcher(webhook.getRef()).matches()) {
                continue;
            }

            JobRun run = new JobRun(
                    job.getId(),
                    webhook.getRepository().getUrl(),
                    webhook.getRef()
            );

            runningJobsManager.addJobRun(run);
            newJobRuns.add(run.getId());

        }
        return newJobRuns;

    }

}
