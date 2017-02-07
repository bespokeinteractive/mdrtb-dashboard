package org.openmrs.module.mdrtbdashboard.model;

import org.openmrs.PatientProgram;

import java.io.Serializable;

/**
 * Created by daugm on 1/14/2017.
 */
public class PatientProgramDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private PatientProgram patientProgram;
    private String tbmuNumber;
    private String labNumber;
    private String details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PatientProgram getPatientProgram() {
        return patientProgram;
    }

    public void setPatientProgram(PatientProgram patientProgram) {
        this.patientProgram = patientProgram;
    }

    public String getTbmuNumber() {
        return tbmuNumber;
    }

    public void setTbmuNumber(String tbmuNumber) {
        this.tbmuNumber = tbmuNumber;
    }

    public String getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(String labNumber) {
        this.labNumber = labNumber;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
