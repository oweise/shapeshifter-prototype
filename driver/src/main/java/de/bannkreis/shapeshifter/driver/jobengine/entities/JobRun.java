package de.bannkreis.shapeshifter.driver.jobengine.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class JobRun {

    private static DateFormat SHORTID_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public String getGitCommit() {
        return gitCommit;
    }

    public String getShortGitCommit() {
        return gitCommit.substring(0, 8);
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
    private Date startDate;

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

    public String getShortId() {
        if (this.startDate == null) {
            throw new IllegalStateException("Job Run " + getId().toString() + " is not yet started");
        }
        String dayQualifier = SHORTID_FORMAT.format(this.startDate);
        String secondQualifier = String.valueOf(
                LocalDate.ofEpochDay(this.startDate.getTime()).get(ChronoField.SECOND_OF_DAY)
        );
        return String.format("%s-%s", dayQualifier, secondQualifier);

    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
