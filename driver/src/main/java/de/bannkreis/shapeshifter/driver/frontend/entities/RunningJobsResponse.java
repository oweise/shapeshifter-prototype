package de.bannkreis.shapeshifter.driver.frontend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RunningJobsResponse {

    public List<UUID> getJobRunIds() {
        return jobRunIds;
    }

    public void setJobRunIds(List<UUID> jobRunIds) {
        this.jobRunIds = jobRunIds;
    }

    @JsonProperty
    private List<UUID> jobRunIds = new ArrayList<>();

}
