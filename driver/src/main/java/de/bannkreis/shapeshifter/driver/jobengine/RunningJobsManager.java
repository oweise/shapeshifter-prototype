package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RunningJobsManager {

    private ConcurrentHashMap<UUID, JobRun> runningJobs = new ConcurrentHashMap<>();

    public void addJobRun(JobRun run) {
        this.runningJobs.put(run.getId(), run);
    }

    public Set<UUID> getRunningJobIds() {
        return this.runningJobs.keySet();
    }

    public JobRun getJobRun(UUID id) {
        return runningJobs.get(id);
    }
}
