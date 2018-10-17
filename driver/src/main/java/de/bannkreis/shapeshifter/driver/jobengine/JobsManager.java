package de.bannkreis.shapeshifter.driver.jobengine;

import com.jetstreamdb.JetstreamDBInstance;
import com.jetstreamdb.util.SizeUnit;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import de.bannkreis.shapeshifter.driver.jobengine.storage.JobStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.*;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobsManager {

    private Logger LOG = LoggerFactory.getLogger(JobsManager.class);

    @Value("${storagepath.jobs}")
    private String storageDirectoryPath;

    private JetstreamDBInstance<JobStorage> jobStorage;

    @PostConstruct
    public void init() {
        File storageDirectory = new File(storageDirectoryPath);
        LOG.info("Storing Job storage in {}", storageDirectory.getAbsolutePath());
        storageDirectory.mkdirs();
        this.jobStorage = JetstreamDBInstance.New("job-storage", JobStorage.class);
        this.jobStorage.properties().setStorageDirectory(storageDirectory);
        this.jobStorage.properties().setStorageChannelCount(4);
        this.jobStorage.properties().setStorageDataFileEvaluatorMaxFileSize((int) SizeUnit.GB.toBytes(2));
        this.jobStorage.start();
    }

    @PreDestroy
    public void destroy() {
        this.jobStorage.shutdown();
    }

    public UUID addJob(Job job) {
        UUID jobId = UUID.randomUUID();
        job.setId(jobId);
        Map<UUID, Job> jobs = jobStorage.root().getJobs();
        jobs.put(jobId, job);
        this.jobStorage.store(jobs);
        return jobId;
    }

    public Set<UUID> getJobIds() {
        return jobStorage.root().getJobs().keySet();
    }

    public Optional<Job> getJob(UUID jobId) {
        return Optional.ofNullable(jobStorage.root().getJobs().get(jobId));
    }

}
