package org.openmrs.module.mdrtbdashboard.model;

import org.openmrs.Location;
import org.openmrs.PatientProgram;
import org.openmrs.User;
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
    private Boolean voided = false;
    private Date voidedOn;
    private Integer voidedBy;
    private String voidReason;

    public PatientProgramTransfers() {
    }

    public PatientProgramTransfers(Integer transferId) {
        this.id = transferId;
    }

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

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    public Date getVoidedOn() {
        return voidedOn;
    }

    public void setVoidedOn(Date voidedOn) {
        this.voidedOn = voidedOn;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }
}
