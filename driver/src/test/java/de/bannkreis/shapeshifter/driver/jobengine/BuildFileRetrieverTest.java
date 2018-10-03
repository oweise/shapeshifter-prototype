package de.bannkreis.shapeshifter.driver.jobengine;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class BuildFileRetrieverTest {

    @Test
    public void shouldRetrieveBuildFile() throws IOException, GitAPIException {

        BuildFileRetriever buildFileRetriever = new BuildFileRetriever();
        String code = buildFileRetriever.retrieveBuildFileCode(
                "https://github.com/oweise/shapeshifter-prototype.git",
                "master",
                "85ee1d9fb9bb494e813f318ac25c2b78b7e90de6"
        );

    }

}