package de.bannkreis.shapeshifter.driver.paas.openshift;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRunState;
import de.bannkreis.shapeshifter.driver.paas.PaasBuild;
import de.bannkreis.shapeshifter.driver.paas.PaasFacade;
import io.fabric8.kubernetes.api.model.EnvFromSourceBuilder;
import io.fabric8.kubernetes.api.model.ObjectReferenceBuilder;
import io.fabric8.openshift.api.model.*;
import io.fabric8.openshift.client.OpenShiftClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(true)
public class OpenShiftFacade implements PaasFacade {

    static final String LABEL_PREFIX = "www.bannkreis.de/";
    static final String LABEL_JOBID = LABEL_PREFIX + "shsh_jobid";
    static final String LABEL_JOBSTEP = LABEL_PREFIX + "shsh_jobstep";


    private final OpenShiftClientProvider openShiftClientProvider;

    public OpenShiftFacade(OpenShiftClientProvider openShiftClientProvider) throws IOException {
        this.openShiftClientProvider = openShiftClientProvider;
    }

    @Override
    public Optional<PaasBuild> getBuild(JobRun jobRun, JobRunState jobRunState) throws IOException {

        // Find an existing build for the given job step
        OpenShiftClient osClient = openShiftClientProvider.createClient();
        Optional<BuildConfig> buildConfig = osClient.buildConfigs()
                .withLabel(LABEL_JOBID, jobRun.getId().toString())
                .withLabel(LABEL_JOBSTEP, jobRunState.getBuildStepName())
                .list().getItems().stream().findFirst();

        if (buildConfig.isPresent()) {
            Build build = osClient.builds()
                    .withName(String.format(
                        "%s-%s",
                        buildConfig.get().getMetadata().getName(),
                        buildConfig.get().getStatus().getLastVersion()))
                    .get();


            return Optional.of(new OpenShiftBuild(buildConfig.get(), build));
        }
        else {
            return Optional.empty();
        }

    }

    @Override

    public PaasBuild createBuild(JobRun jobRun) throws IOException {

        OpenShiftClient osClient = openShiftClientProvider.createClient();

        String buildStepName = String.format("shashi-build-%s-%s",
                jobRun.getJobId().toString(), jobRun.getId().toString());

        SourceBuildStrategy sourceBuildStrategy = new SourceBuildStrategyBuilder()
                .withFrom(
                        new ObjectReferenceBuilder()
                            .withKind("ImageStreamTag")
                            .withName("shapeshifter-buildimage:latest")
                            .build()
                ).build();

        BuildStrategy buildStrategy = new BuildStrategyBuilder()
                .withSourceStrategy(sourceBuildStrategy)
                .build();

        String outputImageName = String.format("%s:latest", buildStepName);

        BuildOutput buildOutput = new BuildOutputBuilder()
                .withNewTo()
                    .withKind("ImageStreamTag")
                    .withName(outputImageName)
                    .endTo()
                .build();

        BuildConfig buildConfig = new BuildConfigBuilder()
                .withNewSpec()
                    .withStrategy(buildStrategy)
                    .withOutput(buildOutput)
                    .endSpec()
                .build();

        return new OpenShiftBuild(buildConfig);
    }
}
