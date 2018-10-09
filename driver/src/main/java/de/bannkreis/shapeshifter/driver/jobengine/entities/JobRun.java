package de.bannkreis.shapeshifter.driver.jobengine.entities;

import java.util.Objects;
import java.util.UUID;

public class JobRun {

    public String getGitCommit() {
        return gitCommit;
    }

    private final String gitCommit;

    public UUID getJobId() {
        return jobId;
    }

    private final UUID jobId;

    public BuildDefinition getBuildDefinition() {
        return buildDefinition;
    }

    public void setBuildDefinition(BuildDefinition buildDefinition) {
        this.buildDefinition = buildDefinition;
    }

    private BuildDefinition buildDefinition;
    private String gitProjectUrl;
    private String gitProjectRef;
    private UUID id;
    private JobRunState state = new JobRunState();

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

    public JobRun(UUID jobId, String gitProjectUrl, String gitProjectRef, String gitCommit) {
        this.id = UUID.randomUUID();
        this.jobId = jobId;
        this.gitProjectUrl = gitProjectUrl;
        this.gitProjectRef = gitProjectRef;
        this.gitCommit = gitCommit;
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
