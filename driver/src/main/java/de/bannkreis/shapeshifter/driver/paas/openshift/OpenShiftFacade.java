package de.bannkreis.shapeshifter.driver.paas.openshift;

import de.bannkreis.shapeshifter.driver.jobengine.JobsManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(true)
public class OpenShiftFacade implements PaasFacade {

    static final String LABEL_PREFIX = "www.bannkreis.de/";
    static final String LABEL_JOBID = LABEL_PREFIX + "shsh_jobid";
    static final String LABEL_JOBSTEP = LABEL_PREFIX + "shsh_jobstep";


    private final OpenShiftClientProvider openShiftClientProvider;

    private final OpenShiftInformationReader openShiftInformationReader;

    private final JobsManager jobManager;

    public OpenShiftFacade(OpenShiftClientProvider openShiftClientProvider,
                           OpenShiftInformationReader openShiftInformationReader,
                           JobsManager jobManager) throws IOException {
        this.openShiftClientProvider = openShiftClientProvider;
        this.openShiftInformationReader = openShiftInformationReader;
        this.jobManager = jobManager;
    }

    @Override
    public Optional<PaasBuild> getBuild(JobRun jobRun, JobRunState jobRunState) throws IOException {

        OpenShiftInformation openShiftInformation = openShiftInformationReader.readOpenShiftInformation();

        // Find an existing build for the given job step
        OpenShiftClient osClient = openShiftClientProvider.createClient(openShiftInformation);

        Optional<BuildConfig> buildConfig = osClient.buildConfigs()
                .inNamespace(openShiftInformation.getNamespace())
                .withLabel(LABEL_JOBID, jobRun.getJobId().toString())
                .withLabel(LABEL_JOBSTEP, jobRunState.getBuildStepName())
                .list().getItems().stream().findFirst();

        if (buildConfig.isPresent()) {
            Build build = osClient.builds()
                    .inNamespace(openShiftInformation.getNamespace())
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

        OpenShiftInformation openShiftInformation = openShiftInformationReader.readOpenShiftInformation();

        OpenShiftClient osClient = openShiftClientProvider.createClient(openShiftInformation);

        Job job = jobManager.getJob(jobRun.getJobId())
                .orElseThrow(()->new IllegalArgumentException("Unknown job id on job run: "
                        + jobRun.getJobId().toString()));

        String buildStepName = String.format("shashi-build-%s-%s-%s",
                job.getName(), jobRun.getState().getBuildStepName(), jobRun.getShortGitCommit()).toLowerCase();

        String outputImageName = String.format("%s:latest", buildStepName);

        // Create output image stream
        ImageStream is = osClient.imageStreams().withName(buildStepName).get();
        if (is == null) {
            is = osClient.imageStreams().createNew()
                    .withNewMetadata()
                    .withName(buildStepName)
                    .endMetadata()
                    .done();
        }

        // Create build config
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

        BuildOutput buildOutput = new BuildOutputBuilder()
                .withNewTo()
                    .withKind("ImageStreamTag")
                    .withName(outputImageName)
                    .endTo()
                .build();

        Map<String,String> labels = new HashMap<>();
        labels.put(LABEL_JOBID, jobRun.getJobId().toString());
        labels.put(LABEL_JOBSTEP, jobRun.getState().getBuildStepName());

        BuildConfig buildConfig = new BuildConfigBuilder()
                .withNewMetadata()
                    .withName(buildStepName)
                    .withLabels(labels)
                    .endMetadata()
                .withNewSpec()
                    .withStrategy(buildStrategy)
                    .withOutput(buildOutput)
                    .endSpec()
                .build();

        osClient.buildConfigs().create(buildConfig);

        return new OpenShiftBuild(buildConfig);
    }

    @Override
    public void startBuild(PaasBuild paasBuild) throws IOException {

        OpenShiftInformation openShiftInformation = openShiftInformationReader.readOpenShiftInformation();

        OpenShiftBuild openShiftBuild = (OpenShiftBuild) paasBuild;
        OpenShiftClient osClient = openShiftClientProvider.createClient(openShiftInformation);

        String buildConfigName = openShiftBuild.getBuildConfig().getMetadata().getName();
        osClient.buildConfigs().withName(buildConfigName)
                .instantiate(new BuildRequestBuilder()
                        .withNewMetadata()
                            .withName(buildConfigName)
                            .endMetadata()
                        .build());
    }
}
