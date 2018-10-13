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
    private final JobsManager jobsManager;

    public BuildDefinitionRetriever(GitSingleFileCodeRetriever gitSingleFileCodeRetriever, JobsManager jobsManager) {
        this.jobsManager = jobsManager;
        this.gitSingleFileCodeRetriever = gitSingleFileCodeRetriever;
    }

    public BuildDefinition retrieveBuildDefinition(JobRun jobRun) throws IOException, GitAPIException {

        Job job = jobsManager.getJob(jobRun.getJobId())
                .orElseThrow(()->new IllegalArgumentException());

        String buildDefinitionCode = this.gitSingleFileCodeRetriever.retrieveCode(
                job.getBuildFilePath(),
                jobRun.getGitProjectUrl(),
                jobRun.getGitProjectRef(),
                jobRun.getGitCommit());

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(buildDefinitionCode, BuildDefinition.class);
    }

}
