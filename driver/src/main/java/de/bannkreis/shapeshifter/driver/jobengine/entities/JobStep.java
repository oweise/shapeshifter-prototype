package de.bannkreis.shapeshifter.driver.jobengine.entities;

import java.util.ArrayList;
import java.util.List;

public class JobStep {

    private String name;
    private String image;
    private List<String> cmd = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getCmd() {
        return cmd;
    }

    public void setCmd(List<String> cmd) {
        this.cmd = cmd;
    }
}
