package org.openmrs.module.mdrtbdashboard.model;

import org.openmrs.Location;
import org.openmrs.PatientProgram;
import java.util.Date;

/**
 * Created by Dennis Henry
 * Created on 9/12/2017.
 */

public class PatientProgramTransfers {
    private Integer id;
    private Location location;
    private Date transferDate;
    private PatientProgram patientProgram;
    private Boolean processed = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public PatientProgram getPatientProgram() {
        return patientProgram;
    }

    public void setPatientProgram(PatientProgram patientProgram) {
        this.patientProgram = patientProgram;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }
}
