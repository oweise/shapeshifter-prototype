package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import de.bannkreis.shapeshifter.driver.paas.PaasBuild;
import de.bannkreis.shapeshifter.driver.paas.PaasFacade;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobRunProcessor {

    private final PaasFacade paasFacade;

    public JobRunProcessor(PaasFacade paasFacade) {
        this.paasFacade = paasFacade;
    }

    public void processJobRun(JobRun jobRun) throws IOException {

        String currentStep = jobRun.getState().getStepName();
        PaasBuild paasBuild = paasFacade.getBuild(jobRun, jobRun.getState())
                .orElse(paasFacade.createBuild(jobRun));

        switch (paasBuild.getBuildState()) {

            case NEW:
                startBuild(paasBuild);

            case RUNNING:
            case FAILED:
                return;

            case COMPLETED:
                startNextStep(jobRun);
        }

    }

    private void startNextStep(JobRun jobRun) {

    }

    private void startBuild(PaasBuild paasBuild) {

    }

}
