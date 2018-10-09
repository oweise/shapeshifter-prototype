package de.bannkreis.shapeshifter.driver.frontend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Webhook {

    @JsonProperty("object_kind")
    private String objectKind;

    private String ref;

    private JobProject project;

    public String getCheckoutSha() {
        return checkoutSha;
    }

    public void setCheckoutSha(String checkoutSha) {
        this.checkoutSha = checkoutSha;
    }

    @JsonProperty("checkout_sha")
    private String checkoutSha;

    @JsonProperty("repository")
    private JobRepository repository;

    public String getObjectKind() {
        return objectKind;
    }

    public void setObjectKind(String objectKind) {
        this.objectKind = objectKind;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public JobProject getProject() {
        return project;
    }

    public void setProject(JobProject project) {
        this.project = project;
    }

    public JobRepository getRepository() {
        return repository;
    }

    public void setRepository(JobRepository repository) {
        this.repository = repository;
    }
}
