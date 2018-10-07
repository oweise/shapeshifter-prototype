package de.bannkreis.shapeshifter.driver.jobengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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

    private final GitSingleFileCodeRetriever gitSingleFileCodeRetriever;
    private final JobManager jobManager;

    public BuildDefinitionRetriever(GitSingleFileCodeRetriever gitSingleFileCodeRetriever, JobManager jobManager) {
        this.jobManager = jobManager;
        this.gitSingleFileCodeRetriever = gitSingleFileCodeRetriever;
    }

    public BuildDefinition retrieveBuildDefinition(JobRun jobRun) throws IOException, GitAPIException {

        Job job = jobManager.getJob(jobRun.getJobId());

        String buildDefinitionCode = this.gitSingleFileCodeRetriever.retrieveCode(
                job.getBuildFilePath(),
                jobRun.getGitProjectUrl(),
                jobRun.getGitProjectRef(),
                "HEAD");

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(buildDefinitionCode, BuildDefinition.class);
    }

}
