package de.bannkreis.shapeshifter.driver.paas.openshift;

import de.bannkreis.shapeshifter.driver.paas.PaasBuild;
import de.bannkreis.shapeshifter.driver.paas.PaasBuildState;
import io.fabric8.openshift.api.model.Build;
import io.fabric8.openshift.api.model.BuildConfig;

import java.util.Set;
import java.util.UUID;

public class OpenShiftBuild implements PaasBuild {

    public BuildConfig getBuildConfig() {
        return buildConfig;
    }

    public OpenShiftBuild(BuildConfig buildConfig, Build lastBuild) {
        this.buildConfig = buildConfig;
        this.lastBuild = lastBuild;
    }

    public OpenShiftBuild(BuildConfig buildConfig) {
        this.buildConfig = buildConfig;
        this.lastBuild = null;
    }

    @Override
    public UUID getJobRunID() {
        return UUID.fromString(buildConfig.getMetadata().getLabels().get(OpenShiftFacade.LABEL_JOBID));
    }

    @Override
    public String getStepName() {
        return buildConfig.getMetadata().getLabels().get(OpenShiftFacade.LABEL_JOBSTEP);
    }

    @Override
    public PaasBuildState getBuildState() {
        if (lastBuild == null) {
            return PaasBuildState.NEW;
        }
        else {
            return OpenShiftBuildPhase.valueOf(lastBuild.getStatus().getPhase()).getPaasBuildState();
        }
    }

    public Build getLastBuild() {
        return lastBuild;
    }

    private final BuildConfig buildConfig;
    private final Build lastBuild;

}
