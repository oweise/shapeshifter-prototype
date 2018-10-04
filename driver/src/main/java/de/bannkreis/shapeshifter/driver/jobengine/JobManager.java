package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobManager {

    private Map<UUID,Job> jobs = new ConcurrentHashMap<>();

    public UUID addJob(Job job) {
        UUID jobId = UUID.randomUUID();
        job.setId(jobId);
        jobs.put(jobId, job);
        return jobId;
    }

    public Set<UUID> getJobIds() {
        return jobs.keySet();
    }

    public Job getJob(UUID jobId) {
        return jobs.get(jobId);
    }

}
