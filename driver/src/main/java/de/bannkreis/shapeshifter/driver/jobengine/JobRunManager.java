package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.BuildStepState;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRunInfo;
import de.bannkreis.shapeshifter.driver.paas.PaasBuild;
import de.bannkreis.shapeshifter.driver.paas.PaasBuildState;
import de.bannkreis.shapeshifter.driver.paas.PaasFacade;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobRunManager {

    private ConcurrentHashMap<UUID, JobRun> runningJobs = new ConcurrentHashMap<>();

    private PaasFacade paasFacade;

    private JobsManager jobsManager;

    public JobRunManager(PaasFacade paasFacade, JobsManager jobsManager) {
        this.paasFacade = paasFacade;
        this.jobsManager = jobsManager;
    }

    public void addJobRun(JobRun run) {
        run.setStartDate(new Date());
        this.runningJobs.put(run.getId(), run);
    }

    public Set<UUID> getRunningJobIds() {
        return this.runningJobs.keySet();
    }

    public Optional<JobRun> getJobRun(UUID id) {
        return Optional.ofNullable(runningJobs.get(id));
    }

    public JobRunInfo getJobRunInfo(UUID id) throws IOException {
        JobRun jobRun = getJobRun(id).orElseThrow(()-> new IllegalArgumentException("Unknown job run ID" + id.toString()));
        Job job = jobsManager.getJob(jobRun.getJobId())
                .orElseThrow(()->new IllegalArgumentException(("Unknown job run: " + id.toString())));
        PaasBuildState paasBuildState = paasFacade.getBuild(jobRun, jobRun.getState())
                .map(PaasBuild::getBuildState).orElse(PaasBuildState.NOT_READY);

        JobRunInfo jobRunInfo = new JobRunInfo();
        jobRunInfo.setStepName(jobRun.getState().getBuildStepName());
        jobRunInfo.setBuildStepState(BuildStepState.fromPaasBuildState(paasBuildState));
        return jobRunInfo;
    }
}
