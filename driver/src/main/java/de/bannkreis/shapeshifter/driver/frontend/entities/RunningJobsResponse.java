package de.bannkreis.shapeshifter.driver.frontend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RunningJobsResponse {

    public List<UUID> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<UUID> jobIds) {
        this.jobIds = jobIds;
    }

    @JsonProperty
    private List<UUID> jobIds = new ArrayList<>();

}
