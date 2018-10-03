package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobDefinition;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BuildFileRetriever {

    public BuildFileRetriever() {
    }

    public JobDefinition retrieveBuildFile(JobRun jobRun) {
        return null;
    }
}
