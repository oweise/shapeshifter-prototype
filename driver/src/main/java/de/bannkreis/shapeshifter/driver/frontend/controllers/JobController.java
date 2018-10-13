package de.bannkreis.shapeshifter.driver.frontend.controllers;

import de.bannkreis.shapeshifter.driver.jobengine.JobsManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value="/jobs",consumes = "application/json", produces="application/json")
public class JobController {

    private final JobsManager jobsManager;

    public JobController(JobsManager jobsManager) {
        this.jobsManager = jobsManager;
    }

    @PostMapping
    public Job postJob(@RequestBody  Job job) {
        jobsManager.addJob(job);
        return job;
    }

    @GetMapping
    public List<String> getJobIds() {
        return jobsManager.getJobIds().stream().map(UUID::toString).collect(Collectors.toList());
    }

    @GetMapping(path="/{jobId}")
    public Job getJob(@PathVariable("jobId") String jobId) {
        return jobsManager.getJob(UUID.fromString(jobId))
                .orElseThrow(()->new IllegalArgumentException());
    }

}
