package de.bannkreis.shapeshifter.driver.jobengine.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.UUID;

public class Job {

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty
    private UUID id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String buildFilePath;

    @JsonProperty
    private String gitUrlPattern;

    @JsonProperty
    private String gitRefPattern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildFilePath() {
        return buildFilePath;
    }

    public void setBuildFilePath(String buildFilePath) {
        this.buildFilePath = buildFilePath;
    }

    public String getGitUrlPattern() {
        return gitUrlPattern;
    }

    public void setGitUrlPattern(String gitUrlPattern) {
        this.gitUrlPattern = gitUrlPattern;
    }

    public String getGitRefPattern() {
        return gitRefPattern;
    }

    public void setGitRefPattern(String gitRefPattern) {
        this.gitRefPattern = gitRefPattern;
    }
}
