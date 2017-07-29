package org.openmrs.module.mdrtbdashboard.model;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dennis Henry
 * Created on 7/18/2017.
 */
public class PatientProgramRegimen implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Concept type;
    private PatientProgramDetails programDetails;
    private Date startedOn;
    private Date finishedOn;
    private String remarks = "N/A";

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

    public PatientProgramDetails getProgramDetails() {
        return programDetails;
    }

    public void setProgramDetails(PatientProgramDetails programDetails) {
        this.programDetails = programDetails;
    }

    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Date startedOn) {
        this.startedOn = startedOn;
    }

    public Date getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(Date finishedOn) {
        this.finishedOn = finishedOn;
    }

    public Concept getType() {
        return type;
    }

    public void setType(Concept type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}