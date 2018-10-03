package de.bannkreis.shapeshifter.driver.paas.openshift;

import de.bannkreis.shapeshifter.driver.paas.PaasBuildState;

public enum OpenShiftBuildPhase {

    NEW("New", PaasBuildState.NEW),
    PENDING("Pending", PaasBuildState.RUNNING),
    RUNNING("Running", PaasBuildState.RUNNING),
    COMPLETE("Complete", PaasBuildState.COMPLETED),
    FAILED("Failed", PaasBuildState.FAILED),
    ERROR("Error", PaasBuildState.FAILED),
    CANCELLED("Cancelled", PaasBuildState.FAILED);

    private final String phaseLiteral;

    public PaasBuildState getPaasBuildState() {
        return paasBuildState;
    }

    private final PaasBuildState paasBuildState;

    private OpenShiftBuildPhase(String phaseLiteral, PaasBuildState paasBuildState) {
        this.phaseLiteral = phaseLiteral;
        this.paasBuildState = paasBuildState;
    }

    @Override
    public String toString() {
        return this.phaseLiteral;
    }


}
