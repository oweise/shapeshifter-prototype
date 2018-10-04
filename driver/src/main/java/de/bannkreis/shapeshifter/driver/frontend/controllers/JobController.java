package de.bannkreis.shapeshifter.driver.frontend.controllers;

import de.bannkreis.shapeshifter.driver.jobengine.JobManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value="/jobs",consumes = "application/json", produces="application/json")
public class JobController {

    private final JobManager jobManager;

    public JobController(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @PostMapping
    public Job postJob(@RequestBody  Job job) {
        jobManager.addJob(job);
        return job;
    }

    @GetMapping
    public List<String> getJobIds() {
        return jobManager.getJobIds().stream().map(UUID::toString).collect(Collectors.toList());
    }

    @GetMapping(path="/{jobId}")
    public Job getJob(@PathVariable("jobId") String jobId) {
        return jobManager.getJob(UUID.fromString(jobId));
    }

}
