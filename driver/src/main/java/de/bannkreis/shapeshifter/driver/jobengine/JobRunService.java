package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.BuildStepState;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRunInfo;
import de.bannkreis.shapeshifter.driver.paas.PaasBuild;
import de.bannkreis.shapeshifter.driver.paas.PaasFacade;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobRunService {

    private ConcurrentHashMap<UUID, JobRun> runningJobs = new ConcurrentHashMap<>();

    private PaasFacade paasFacade;

    private JobsService jobsService;

    public JobRunService(PaasFacade paasFacade, JobsService jobsService) {
        this.paasFacade = paasFacade;
    }

    public void addJobRun(JobRun run) {
        this.runningJobs.put(run.getId(), run);
    }

    public Set<UUID> getRunningJobIds() {
        return this.runningJobs.keySet();
    }

    public JobRun getJobRun(UUID id) {
        return runningJobs.get(id);
    }

    public JobRunInfo getJobRunInfo(UUID id) throws IOException {
        JobRun jobRun = getJobRun(id);
        Job job = jobsService.getJob(jobRun.getJobId())
                .orElseThrow(()->new IllegalArgumentException(("Unknown job run: " + id.toString())));
        PaasBuild paasBuild = paasFacade.getBuild(jobRun, jobRun.getState())
                .orElseThrow(()->new IllegalArgumentException());

        JobRunInfo jobRunInfo = new JobRunInfo();
        jobRunInfo.setStepName(jobRun.getState().getBuildStepName());
        jobRunInfo.setBuildStepState(BuildStepState.fromPaasBuildState(paasBuild.getBuildState()));
        return jobRunInfo;
    }
}
