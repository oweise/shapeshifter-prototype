package de.bannkreis.shapeshifter.driver.frontend.controllers;

import de.bannkreis.shapeshifter.driver.frontend.entities.RunningJobsResponse;
import de.bannkreis.shapeshifter.driver.jobengine.JobRunManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRunInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value="/jobruns", consumes = "application/json", produces = "application/json")
public class JobRunController {

    private JobRunManager jobRunManager;

    public JobRunController(JobRunManager jobRunManager) {
        this.jobRunManager = jobRunManager;
    }

    @GetMapping
    public RunningJobsResponse getRunningJobs() {
        RunningJobsResponse runningJobsResponse = new RunningJobsResponse();

        runningJobsResponse.getJobRunIds().addAll(jobRunManager.getRunningJobIds());

        return runningJobsResponse;
    }

    @GetMapping(path="/{jobId}")
    public JobRunInfo getJobRunInfo(@PathVariable("jobId") String jobId) throws IOException {
        return jobRunManager.getJobRunInfo(UUID.fromString(jobId));
    }

}
