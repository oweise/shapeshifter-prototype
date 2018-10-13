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
                "36c18a7cb130cf769677a503432d722e4f178fb1"
        );

        String expectedCode = new String(
                Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("shashi.yml").toURI())), "UTF8");
        Assert.assertEquals(expectedCode, code);

    }

}