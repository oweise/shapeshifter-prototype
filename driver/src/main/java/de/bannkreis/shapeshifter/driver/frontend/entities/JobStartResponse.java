package de.bannkreis.shapeshifter.driver.frontend.entities;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.UUID;

public class JobStartResponse {

    private UUID newJobId;

    private ControllerError error;

    public UUID getNewJobId() {
        return newJobId;
    }

    public void setNewJobId(UUID newJobId) {
        this.newJobId = newJobId;
    }

    public JobStartResponse jobId(UUID newJobId) {
        this.newJobId = newJobId;
        return this;
    }

    public JobStartResponse error(String type, String message) {
        this.error = new ControllerError();
        error.setType(type);
        error.setMessage(message);
        return this;
    }
}
