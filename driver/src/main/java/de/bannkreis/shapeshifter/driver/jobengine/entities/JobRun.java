package de.bannkreis.shapeshifter.driver.jobengine.entities;

import java.util.Objects;
import java.util.UUID;

public class JobRun {

    private String gitProjectUrl;
    private UUID id;
    private JobRunState state;

    public JobRun(String gitProjectUrl) {
        this.id = UUID.randomUUID();
        this.gitProjectUrl = gitProjectUrl;
    }

    public UUID getId() {
        return id;
    }


    public String getGitProjectUrl() {
        return gitProjectUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRun jobRun = (JobRun) o;
        return Objects.equals(gitProjectUrl, jobRun.gitProjectUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gitProjectUrl);
    }

    public JobRunState getState() {
        return state;
    }

    public void setState(JobRunState state) {
        this.state = state;
    }
}
