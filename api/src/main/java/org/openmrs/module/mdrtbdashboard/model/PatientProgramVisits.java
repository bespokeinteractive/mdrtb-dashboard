package org.openmrs.module.mdrtbdashboard.model;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.PatientProgram;

import java.util.Date;

/**
 * Created by Dennis Henry
 * Created on 8/3/2017.
 */
public class PatientProgramVisits {
    private Integer id;
    private Encounter encounter;
    private PatientProgram patientProgram;
    private VisitTypes visitType;
    private String labNumber;
    private Date examDate;
    private Concept sputumResult;
    private Concept cultureResults;
    private Concept geneXpertResult;
    private Concept hivResults;
    private Concept xrayResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public PatientProgram getPatientProgram() {
        return patientProgram;
    }

    public void setPatientProgram(PatientProgram patientProgram) {
        this.patientProgram = patientProgram;
    }

    public VisitTypes getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitTypes visitType) {
        this.visitType = visitType;
    }

    public String getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(String labNumber) {
        this.labNumber = labNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Concept getSputumResult() {
        return sputumResult;
    }

    public void setSputumResult(Concept sputumResult) {
        this.sputumResult = sputumResult;
    }

    public Concept getGeneXpertResult() {
        return geneXpertResult;
    }

    public void setGeneXpertResult(Concept geneXpertResult) {
        this.geneXpertResult = geneXpertResult;
    }

    public Concept getHivResults() {
        return hivResults;
    }

    public void setHivResults(Concept hivResults) {
        this.hivResults = hivResults;
    }

    public Concept getXrayResults() {
        return xrayResults;
    }

    public void setXrayResults(Concept xrayResults) {
        this.xrayResults = xrayResults;
    }

    public Concept getCultureResults() {
        return cultureResults;
    }

    public void setCultureResults(Concept cultureResults) {
        this.cultureResults = cultureResults;
    }
}
