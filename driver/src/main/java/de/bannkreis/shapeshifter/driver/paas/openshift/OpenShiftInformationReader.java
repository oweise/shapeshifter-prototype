package de.bannkreis.shapeshifter.driver.paas.openshift;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OpenShiftInformationReader {

    public OpenShiftInformation readOpenShiftInformation() throws IOException {

        File infoDirectory = new File("/var/run/secrets/kubernetes.io/serviceaccount");
        OpenShiftInformation openShiftInformation = new OpenShiftInformation();

        File nameSpaceFile = new File(infoDirectory, "namespace");
        openShiftInformation.setNamespace(
                new String(Files.readAllBytes(Paths.get(nameSpaceFile.toURI())), "UTF-8")
        );

        File saTokenFile = new File(infoDirectory, "token");
        openShiftInformation.setSaToken(
                new String(Files.readAllBytes(Paths.get(saTokenFile.toURI())), "UTF-8")
        );

        File caFile = new File(infoDirectory, "ca.crt");
        openShiftInformation.setCa(
                new String(Files.readAllBytes(Paths.get(caFile.toURI())), "UTF-8")
        );

        File serviceCaFile = new File(infoDirectory, "service-ca.crt");
        openShiftInformation.setServiceCa(
                new String(Files.readAllBytes(Paths.get(serviceCaFile.toURI())), "UTF-8")
        );

        return openShiftInformation;

    }
}
