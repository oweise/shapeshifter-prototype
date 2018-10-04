package de.bannkreis.shapeshifter.driver.jobengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bannkreis.shapeshifter.driver.jobengine.entities.BuildDefinition;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BuildDefinitionRetriever {

    private final BuildDefinitionCodeRetriever buildDefinitionCodeRetriever;
    private final JobManager jobManager;

    public BuildDefinitionRetriever(BuildDefinitionCodeRetriever buildDefinitionCodeRetriever, JobManager jobManager) {
        this.jobManager = jobManager;
        this.buildDefinitionCodeRetriever = buildDefinitionCodeRetriever;
    }

    public BuildDefinition retrieveBuildDefinition(JobRun jobRun) throws IOException, GitAPIException {

        Job job = jobManager.getJob(jobRun.getJobId());

        String buildDefinitionCode = this.buildDefinitionCodeRetriever.retrieveBuildDefinitionCode(
                job.getBuildFilePath(),
                jobRun.getGitProjectUrl(),
                jobRun.getGitProjectRef(),
                "HEAD");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(buildDefinitionCode, BuildDefinition.class);
    }

}
