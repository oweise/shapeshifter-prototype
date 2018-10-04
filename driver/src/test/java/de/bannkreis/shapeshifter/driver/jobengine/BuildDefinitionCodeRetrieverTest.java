package de.bannkreis.shapeshifter.driver.jobengine;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.IOException;

public class BuildDefinitionCodeRetrieverTest {

    @Test
    public void shouldRetrieveBuildFile() throws IOException, GitAPIException {

        BuildDefinitionCodeRetriever buildFileRetriever = new BuildDefinitionCodeRetriever();
        String code = buildFileRetriever.retrieveBuildDefinitionCode(
                "demo-project/shashi.yml",
                "https://github.com/oweise/shapeshifter-prototype.git",
                "master",
                "85ee1d9fb9bb494e813f318ac25c2b78b7e90de6"
        );

    }

}