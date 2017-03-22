package org.openmrs.module.mdrtbdashboard.util;

import org.openmrs.Patient;

import java.util.Date;
import java.util.List;

/**
 * Created by Dennis Henry on 3/21/2017.
 */
public class ResultModelWrapper {
    private Patient patient;
    private Date testDate;
    private String labNumber;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    private List<ResultModel> results;

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getLabNumber() { return labNumber; }

    public void setLabNumber(String labNumber) {
        this.labNumber = labNumber;
    }

    public List<ResultModel> getResults() {
        return results;
    }

    public void setResults(List<ResultModel> results) {
        this.results = results;
    }
}
