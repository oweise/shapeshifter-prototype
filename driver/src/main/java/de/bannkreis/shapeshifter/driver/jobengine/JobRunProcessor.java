package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JobRunProcessor {

    public void processJobRun(JobRun jobRun) {

        String currentStep = jobRun.getState().getStepName();

        // Search for a build config for this step


    }

}
