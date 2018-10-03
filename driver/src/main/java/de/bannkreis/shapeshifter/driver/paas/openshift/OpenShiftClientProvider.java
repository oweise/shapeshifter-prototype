package de.bannkreis.shapeshifter.driver.paas.openshift;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(true)
public class OpenShiftClientProvider {

    private final OpenShiftInformationReader openShiftInformationReader;

    public OpenShiftClientProvider(OpenShiftInformationReader openShiftInformationReader) {
        this.openShiftInformationReader = openShiftInformationReader;
    }

    public OpenShiftClient createClient() throws IOException {

        OpenShiftInformation openShiftInformation = openShiftInformationReader.readOpenShiftInformation();

        Config config = new ConfigBuilder()
                .withMasterUrl("https://kubernetes.default/api/v1")
                .withUsername(openShiftInformation.getSaToken())
                .withCaCertData(openShiftInformation.getCa())
                .withNamespace(openShiftInformation.getNamespace())
                .build();

        return new DefaultOpenShiftClient(config);
    }

}
