package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.InternalErrorException;
import de.bannkreis.shapeshifter.driver.jobengine.entities.BuildDefinition;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import de.bannkreis.shapeshifter.driver.paas.PaasBuild;
import de.bannkreis.shapeshifter.driver.paas.PaasFacade;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobRunProcessor {

    private final PaasFacade paasFacade;
    private final BuildDefinitionRetriever buildDefinitionRetriever;

    public JobRunProcessor(PaasFacade paasFacade, BuildDefinitionRetriever buildDefinitionRetriever) {
        this.paasFacade = paasFacade;
        this.buildDefinitionRetriever = buildDefinitionRetriever;
    }

    public void processJobRun(JobRun jobRun) throws IOException, GitAPIException {

        // Initialize a new job run
        if (jobRun.getBuildDefinition() == null) {
            jobRun.setBuildDefinition(buildDefinitionRetriever.retrieveBuildDefinition(jobRun));
        }

        // Initialize the build step name
        if (jobRun.getState().getBuildStepName() == null) {
            jobRun.getState().setBuildStepName(jobRun.getBuildDefinition().getBuildSteps().get(0).getName());
        }

        // Search for an OS build entity
        PaasBuild paasBuild = paasFacade.getBuild(jobRun, jobRun.getState())
                .orElseGet(InternalErrorException.handle(()->paasFacade.createBuild(jobRun)));

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

    private void startBuild(PaasBuild paasBuild) throws IOException {
        paasFacade.startBuild(paasBuild);
    }

}
