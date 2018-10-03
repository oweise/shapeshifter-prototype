package de.bannkreis.shapeshifter.driver.paas;

import java.util.UUID;

public interface PaasBuild {

    public UUID getJobRunID();

    public String getStepName();

    public PaasBuildState getBuildState();

}
