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
                "8d5e5d7b956b887554d6db002c9b1a30991829a0"
        );

        String expectedCode = new String(
                Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("shashi.yml").toURI())), "UTF8");
        Assert.assertEquals(expectedCode, code);

    }

}