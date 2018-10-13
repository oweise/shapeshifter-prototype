package de.bannkreis.shapeshifter.driver.jobengine.entities;

public class JobRunInfo {

    private String stepName;
    private BuildStepState buildStepState;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }


    public BuildStepState getBuildStepState() {
        return buildStepState;
    }

    public void setBuildStepState(BuildStepState buildStepState) {
        this.buildStepState = buildStepState;
    }
}
