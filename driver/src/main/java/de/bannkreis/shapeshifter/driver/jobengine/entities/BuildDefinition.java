package de.bannkreis.shapeshifter.driver.jobengine.entities;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BuildDefinition {

    private List<URI> libs = new ArrayList<>();

    @JsonProperty("steps")
    private List<BuildStep> buildSteps = new ArrayList<>();

    public List<BuildStep> getBuildSteps() {
        return buildSteps;
    }

    public BuildStep findBuildStep(String name) {
        return buildSteps.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Unknown step name: " + name));
    }

    public void setBuildSteps(List<BuildStep> buildSteps) {
        this.buildSteps = buildSteps;
    }

    public List<URI> getLibs() {
        return libs;
    }

    public void setLibs(List<URI> libs) {
        this.libs = libs;
    }
}
