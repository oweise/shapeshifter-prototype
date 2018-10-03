package de.bannkreis.shapeshifter.driver.paas.openshift;

public class OpenShiftInformation {

    private String saToken;
    private String namespace;
    private String ca;
    private String serviceCa;


    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSaToken() {
        return saToken;
    }

    public void setSaToken(String saToken) {
        this.saToken = saToken;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getServiceCa() {
        return serviceCa;
    }

    public void setServiceCa(String serviceCa) {
        this.serviceCa = serviceCa;
    }
}
