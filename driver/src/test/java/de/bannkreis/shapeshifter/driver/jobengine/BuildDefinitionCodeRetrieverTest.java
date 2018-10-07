package de.bannkreis.shapeshifter.driver.jobengine;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BuildDefinitionCodeRetrieverTest {

    @Test
    public void shouldRetrieveBuildFile() throws IOException, GitAPIException, URISyntaxException {

        GitSingleFileCodeRetriever buildFileRetriever = new GitSingleFileCodeRetriever();
        String code = buildFileRetriever.retrieveCode(
                "demo-project/shashi.yml",
                "https://github.com/oweise/shapeshifter-prototype.git",
                "master",
                "85ee1d9fb9bb494e813f318ac25c2b78b7e90de6"
        );

        String expectedCode = new String(
                Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("shashi.yml").toURI())), "UTF8");
        Assert.assertEquals(expectedCode, code);

    }

}