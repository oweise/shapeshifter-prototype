package de.bannkreis.shapeshifter.driver.jobengine.storage;

import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class JobStorage {

    public Map<UUID, Job> getJobs() {
        return jobs;
    }

    public void setJobs(Map<UUID, Job> jobs) {
        this.jobs = jobs;
    }

    private Map<UUID,Job> jobs = new ConcurrentHashMap<>();

}
