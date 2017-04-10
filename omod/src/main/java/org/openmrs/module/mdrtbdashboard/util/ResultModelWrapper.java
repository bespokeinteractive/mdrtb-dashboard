package org.openmrs.module.mdrtbdashboard.util;

import org.openmrs.Patient;
import org.openmrs.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Dennis Henry on 3/21/2017.
 */
public class ResultModelWrapper {
    private Patient patient;
    private Date testDate;
    private String labNumber;
    private User user;
    private String names;
    private String gender;
    private String username;
    private String password;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
