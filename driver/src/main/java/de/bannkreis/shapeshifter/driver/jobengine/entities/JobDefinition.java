package de.bannkreis.shapeshifter.driver.jobengine.entities;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class JobDefinition {

    private List<URI> libs = new ArrayList<>();
    private List<JobStep> jobStages = new ArrayList<>();

}
