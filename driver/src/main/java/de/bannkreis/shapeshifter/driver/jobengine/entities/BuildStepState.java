package de.bannkreis.shapeshifter.driver.jobengine.entities;

import de.bannkreis.shapeshifter.driver.paas.PaasBuildState;

public enum BuildStepState {

    NEW,
    RUNNING,
    FAILED,
    DONE;


    public static BuildStepState fromPaasBuildState(PaasBuildState paasBuildState) {

        switch (paasBuildState) {

            case RUNNING:
                return RUNNING;

            case FAILED:
                return FAILED;

            case COMPLETED:
                return DONE;

            default:
                return NEW;

        }

    }

}
