package de.bannkreis.shapeshifter.driver.paas;

import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRun;
import de.bannkreis.shapeshifter.driver.jobengine.entities.JobRunState;

import java.io.IOException;
import java.util.Optional;

public interface PaasFacade {

    public Optional<PaasBuild> getBuild(JobRun jobRun, JobRunState jobRunState) throws IOException;

}
