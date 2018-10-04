package de.bannkreis.shapeshifter.driver.frontend.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JobStartResponse {

    public List<UUID> getNewJobRunIds() {
        return newJobRunIds;
    }

    public void setNewJobRunIds(List<UUID> newJobRunIds) {
        this.newJobRunIds = newJobRunIds;
    }

    private List<UUID> newJobRunIds = new ArrayList<>();

    public ControllerError getError() {
        return error;
    }

    public void setError(ControllerError error) {
        this.error = error;
    }

    private ControllerError error;

    public JobStartResponse jobRunIds(List<UUID> newJobId) {
        this.newJobRunIds.addAll(newJobId);
        return this;
    }

    public JobStartResponse error(String type, String message) {
        this.error = new ControllerError();
        error.setType(type);
        error.setMessage(message);
        return this;
    }
}
