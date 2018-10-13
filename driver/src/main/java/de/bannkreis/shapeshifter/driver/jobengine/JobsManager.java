package de.bannkreis.shapeshifter.driver.jobengine;

import com.jetstreamdb.JetstreamDBInstance;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import de.bannkreis.shapeshifter.driver.jobengine.storage.JobStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobsManager {

    @Value("${storagepath.jobs}")
    private String storageDirectory;

    private JetstreamDBInstance<JobStorage> jobStorage;

    @PostConstruct
    public void init() {
        new File(storageDirectory).mkdirs();
        this.jobStorage = JetstreamDBInstance.New(JobStorage.class);
        this.jobStorage.properties().setStorageDirectory(storageDirectory);
    }

    public UUID addJob(Job job) {
        UUID jobId = UUID.randomUUID();
        job.setId(jobId);
        jobStorage.root().getJobs().put(jobId, job);
        this.jobStorage.store(job);
        return jobId;
    }

    public Set<UUID> getJobIds() {
        return jobStorage.root().getJobs().keySet();
    }

    public Optional<Job> getJob(UUID jobId) {
        return Optional.ofNullable(jobStorage.root().getJobs().get(jobId));
    }

}
