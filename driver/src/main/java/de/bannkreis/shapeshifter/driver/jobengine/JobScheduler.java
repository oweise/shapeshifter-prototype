package de.bannkreis.shapeshifter.driver.jobengine;

public class JobScheduler {

    private final RunningJobsManager runningJobsManager;

    public JobScheduler(RunningJobsManager runningJobsManager) {
        this.runningJobsManager = runningJobsManager;
    }
}
