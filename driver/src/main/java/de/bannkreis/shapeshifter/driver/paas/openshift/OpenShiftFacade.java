package de.bannkreis.shapeshifter.driver.paas.openshift;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRunState;
import de.bannkreis.shapeshifter.driver.paas.PaasBuild;
import de.bannkreis.shapeshifter.driver.paas.PaasFacade;
import io.fabric8.openshift.api.model.Build;
import io.fabric8.openshift.api.model.BuildConfig;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import io.fabric8.openshift.client.dsl.BuildResource;
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
                .withLabel(LABEL_JOBSTEP, jobRunState.getStepName())
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
}
