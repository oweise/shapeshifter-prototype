package de.bannkreis.shapeshifter.driver.frontend.controllers;

import de.bannkreis.shapeshifter.driver.frontend.entities.JobStartRequest;
import de.bannkreis.shapeshifter.driver.frontend.entities.JobStartResponse;
import de.bannkreis.shapeshifter.driver.jobengine.RunningJobsManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value="/jobs",consumes = "application/json", produces="application/json")
public class JobController {

    private final RunningJobsManager runningJobsManager;

    public JobController(RunningJobsManager runningJobsManager) {
        this.runningJobsManager = runningJobsManager;
    }

    @PostMapping
    public JobStartResponse startJob(@RequestBody JobStartRequest jobStart) {
        JobRun run = new JobRun(
                jobStart.getRepository().getUrl(),
                jobStart.getRef()
        );
        runningJobsManager.addJobRun(run);
        return new JobStartResponse().jobId(run.getId());
    }

    @GetMapping
    public Set<String> getRunningJobIds() {
        return runningJobsManager.getRunningJobIds()
                .stream().map(UUID::toString).collect(Collectors.toSet());
    }

}
