package org.openmrs.module.mdrtbdashboard.model;

import org.openmrs.Concept;
import org.openmrs.Program;
import java.io.Serializable;

/**
 * Created by Dennis Henry
 * Created on 7/18/2017.
 */
public class RegimentType implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Concept concept;
    private Program program;
    private Integer voided;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Integer getVoided() {
        return voided;
    }

    public void setVoided(Integer voided) {
        this.voided = voided;
    }
}
