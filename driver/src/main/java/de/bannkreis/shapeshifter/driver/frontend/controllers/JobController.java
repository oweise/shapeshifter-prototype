package de.bannkreis.shapeshifter.driver.frontend.controllers;

import de.bannkreis.shapeshifter.driver.jobengine.JobsService;
import de.bannkreis.shapeshifter.driver.jobengine.entities.Job;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value="/jobs",consumes = "application/json", produces="application/json")
public class JobController {

    private final JobsService jobsService;

    public JobController(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    @PostMapping
    public Job postJob(@RequestBody  Job job) {
        jobsService.addJob(job);
        return job;
    }

    @GetMapping
    public List<String> getJobIds() {
        return jobsService.getJobIds().stream().map(UUID::toString).collect(Collectors.toList());
    }

    @GetMapping(path="/{jobId}")
    public Job getJob(@PathVariable("jobId") String jobId) {
        return jobsService.getJob(UUID.fromString(jobId))
                .orElseThrow(()->new IllegalArgumentException());
    }

}
