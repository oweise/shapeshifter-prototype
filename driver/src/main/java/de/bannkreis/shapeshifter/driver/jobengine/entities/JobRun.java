package de.bannkreis.shapeshifter.driver.jobengine.entities;

import java.util.Objects;
import java.util.UUID;

public class JobRun {

    private String gitProjectUrl;
    private String gitProjectRef;
    private UUID id;
    private JobRunState state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRun jobRun = (JobRun) o;
        return Objects.equals(gitProjectUrl, jobRun.gitProjectUrl) &&
                Objects.equals(gitProjectRef, jobRun.gitProjectRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gitProjectUrl, gitProjectRef);
    }

    public JobRun(String gitProjectUrl, String gitProjectRef) {
        this.id = UUID.randomUUID();
        this.gitProjectUrl = gitProjectUrl;
        this.gitProjectRef = gitProjectRef;
    }

    public UUID getId() {
        return id;
    }


    public String getGitProjectUrl() {
        return gitProjectUrl;
    }


    public JobRunState getState() {
        return state;
    }

    public void setState(JobRunState state) {
        this.state = state;
    }

    public String getGitProjectRef() {
        return gitProjectRef;
    }

    public void setGitProjectRef(String gitProjectRef) {
        this.gitProjectRef = gitProjectRef;
    }
}
