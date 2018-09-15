package de.bannkreis.shapeshifter.driver.controllers.controllers;

import de.bannkreis.shapeshifter.driver.jobengine.RunningJobsManager;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller("/jobs")
public class JobController {

    private final RunningJobsManager runningJobsManager;

    public JobController(RunningJobsManager runningJobsManager) {
        this.runningJobsManager = runningJobsManager;
    }

    @PostMapping
    public String startJob(String gitProjectUrl, String gitJobLibraryUrl) {
        JobRun run = new JobRun(gitProjectUrl, gitJobLibraryUrl);
        runningJobsManager.addJobRun(run);
        return run.getId().toString();
    }

    @GetMapping
    public Set<String> getRunningJobIds() {
        return runningJobsManager.getRunningJobIds()
                .stream().map(UUID::toString).collect(Collectors.toSet());
    }

}
