package de.bannkreis.shapeshifter.driver.jobengine.entities;

import java.util.Objects;
import java.util.UUID;

public class JobRun {

    private final String gitJobLibraryUrl;
    private String gitProjectUrl;
    private UUID id;

    public JobRun(String gitProjectUrl, String gitJobLibraryUrl) {
        this.id = UUID.randomUUID();
        this.gitProjectUrl = gitProjectUrl;
        this.gitJobLibraryUrl = gitJobLibraryUrl;
    }

    public UUID getId() {
        return id;
    }

    public String getGitJobLibraryUrl() {
        return gitJobLibraryUrl;
    }

    public String getGitProjectUrl() {
        return gitProjectUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRun jobRun = (JobRun) o;
        return Objects.equals(gitJobLibraryUrl, jobRun.gitJobLibraryUrl) &&
                Objects.equals(gitProjectUrl, jobRun.gitProjectUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gitJobLibraryUrl, gitProjectUrl);
    }
}
