package de.bannkreis.shapeshifter.driver.jobengine;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GitSingleFileCodeRetriever {

    public GitSingleFileCodeRetriever() {
    }

    public String retrieveCode(String filePath, String gitUri, String gitRef, String commit) throws IOException, GitAPIException {

        File tempCheckoutDir = File.createTempFile("shashi", ".tmp");
        tempCheckoutDir.delete();
        tempCheckoutDir.mkdirs();
        try {

            Git gitRepo = Git.cloneRepository()
                    .setURI(gitUri)
                    .setDirectory(tempCheckoutDir)
                    .setBranch(gitRef)
                    .setNoCheckout(true)
                    .call();

            gitRepo.checkout().setStartPoint(commit).addPath(filePath).call();
            gitRepo.getRepository().close();

            File shashiFile = new File(tempCheckoutDir, filePath);
            String shashiCode = Files.readAllLines(Paths.get(shashiFile.toURI()), Charset.forName("UTF-8"))
                    .stream().collect(Collectors.joining("\n"));

            return shashiCode;
        }
        finally {
            tempCheckoutDir.delete();
        }


    }
}
