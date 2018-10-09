package de.bannkreis.shapeshifter.driver.jobengine;

import de.bannkreis.shapeshifter.driver.jobengine.entities.BuildDefinition;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.*;

public class BuildDefinitionRetrieverTest {

    @Test
    public void shouldRetrieveBuildDefinition() throws IOException, GitAPIException, URISyntaxException {

        // GIVEN
        UUID theJobId = UUID.randomUUID();
        String gitUrl = "https://github.com/oweise/shapeshifter-prototype.git/demo-lib";

        Job job = Mockito.mock(Job.class);
        Mockito.when(job.getBuildFilePath()).thenReturn("shashi.yml");

        JobManager jobManager = Mockito.mock(JobManager.class);
        Mockito.when(jobManager.getJob(Mockito.eq(theJobId))).thenReturn(job);

        String buildCode = new String(
                Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("shashi.yml").toURI())), "UTF8");
        GitSingleFileCodeRetriever gitSingleFileCodeRetriever = Mockito.mock(GitSingleFileCodeRetriever.class);
        Mockito.when(gitSingleFileCodeRetriever.retrieveCode(
                Mockito.eq("shashi.yml"),
                Mockito.eq(gitUrl),
                Mockito.eq("refs/heads/master"),
                Mockito.eq("HEAD")
        )).thenReturn(buildCode);


        JobRun jobRun = Mockito.mock(JobRun.class);
        Mockito.when(jobRun.getJobId()).thenReturn(theJobId);
        Mockito.when(jobRun.getGitProjectRef()).thenReturn("refs/heads/master");
        Mockito.when(jobRun.getGitProjectUrl()).thenReturn(gitUrl);
        Mockito.when(jobRun.getGitCommit()).thenReturn("HEAD");

        // WHEN
        BuildDefinitionRetriever buildDefinitionRetriever = new BuildDefinitionRetriever(gitSingleFileCodeRetriever,
                jobManager);

        BuildDefinition buildDefinition = buildDefinitionRetriever.retrieveBuildDefinition(jobRun);

        // THEN
        assertEquals(1, buildDefinition.getLibs().size());
        assertEquals(new URI(gitUrl),
                buildDefinition.getLibs().get(0));

        assertEquals(1, buildDefinition.getBuildSteps().size());
        assertEquals("do_something", buildDefinition.getBuildSteps().get(0).getName());

    }

}