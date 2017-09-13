package org.openmrs.module.mdrtbdashboard;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.model.PersonLocation;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramRegimen;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Dennis Henry
 * Created on 2/2/2017.
 */
public class MdrtbPatientWrapper {
    private MdrtbPatientProgram patientProgram;
    private PatientProgramDetails patientDetails;
    private String wrapperIdentifier = "N/A";
    private String wrapperNames = "N/A";
    private String wrapperStatus = "Completed";
    private String wrapperReason = "Confirmed";
    private String formartedVisitDate;
    private Date visitDate = new Date();
    private Integer wrapperLocationId;
    private String wrapperLocationName;
    private String wrapperAddress;
    private String wrapperArt = "—";
    private String wrapperCpt = "—";
    private String wrapperOutcome = "—";
    private String wrapperRegisterDate = "N/A";
    private String wrapperCompletedDate = "N/A";
    private String wrapperTreatmentDate = "N/A";
    private String wrapperSecondLineDate = "N/A";


    MdrtbDashboardService dashboardService = Context.getService(MdrtbDashboardService.class);
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    private static final Logger log = Logger.getLogger(MdrtbPatientWrapper.class.getName());
    public MdrtbPatientWrapper(){
    }

    public MdrtbPatientWrapper(MdrtbPatientProgram patientProgram){
        this.patientProgram = patientProgram;
        this.getPatientDefaultLocation();
        this.getPatientProgramDetails();
    }

    public MdrtbPatientWrapper(MdrtbPatientProgram patientProgram, Date visitDate){
        this(patientProgram);
        this.visitDate = visitDate;
    }

    private void getPatientDefaultLocation(){
        Patient patient = patientProgram.getPatient();
        PersonLocation pl = mdrtbService.getPersonLocation(patient);

        this.wrapperLocationId = pl.getLocation().getId();
        this.wrapperLocationName = pl.getLocation().getName();
    }

    private void getPatientProgramDetails(){
        try{
            this.patientDetails = dashboardService.getPatientProgramDetails(patientProgram.getPatientProgram());
            this.wrapperIdentifier = patientDetails.getTbmuNumber();

            if (patientDetails.getInitialStatus() != null || patientDetails.getCurrentStatus() != null) {
                if (patientDetails.getInitialStatus().getId() == 28 && patientDetails.getCurrentStatus().getId() == 28){
                    this.wrapperArt = patientDetails.getArtStarted().getDisplayString();
                    this.wrapperCpt = patientDetails.getCptStarted().getDisplayString();
                }
            }

            if (patientProgram.getPatientProgram().getDateCompleted() != null){
                this.wrapperCompletedDate = returnFormattedDate(patientProgram.getPatientProgram().getDateCompleted());
            }

            if (patientDetails.getOutcome() != null){
                this.wrapperOutcome = patientDetails.getOutcome().getDisplayString();
            }

            if (patientDetails.getSecondLineDate() != null){
                this.wrapperSecondLineDate = returnFormattedDate(patientDetails.getSecondLineDate());
            }

            if (patientDetails.getConfirmationSite().equals(mdrtbService.getConcept(MdrtbConcepts.BACTERIOLOGICAL_CONFIRMED))){
                this.wrapperReason = "CONFIRMED";
            }
            else {
                this.wrapperReason = "PRESUMPTIVE";
            }
        }
        catch (Exception e){ }
    }

    public String returnFormattedDate(Date date){
        Format formatter = new SimpleDateFormat("dd/MM/yy");

        try{
            return formatter.format(date);
        }catch(Exception e){
            if(date != null){
                return date.toString();
            } else{
                return "N/A";
            }
        }
    }

    public String returnShortenedConceptName(Concept concept){
        if (concept == null){
            return "N/A";
        }
        else if (concept.equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE))){
            return "POS";
        }
        else if (concept.equals(mdrtbService.getConcept(MdrtbConcepts.NEGATIVE))){
            return "NEG";
        }
        else if (concept.equals(mdrtbService.getConcept(MdrtbConcepts.NOT_DONE))){
            return "N/D";
        }
        else if (concept.equals(mdrtbService.getConcept(MdrtbConcepts.SUGGESTIVE))){
            return "SUGGESTIVE";
        }
        else if (concept.equals(mdrtbService.getConcept(MdrtbConcepts.NOT_SUGGESTIVE))){
            return "N/S";
        }
        else if (concept.equals(mdrtbService.getConcept(MdrtbConcepts.INVALID))){
            return "INVALID";
        }
        else {
            return "N/A";
        }
    }

    public String getWrapperIdentifier() {
        return wrapperIdentifier;
    }

    public void setWrapperIdentifier(String wrapperIdentifier) {
        this.wrapperIdentifier = wrapperIdentifier;
    }

    public String getFormartedVisitDate() {
        this.formartedVisitDate = returnFormattedDate(visitDate);
        return this.formartedVisitDate;
    }

    public MdrtbPatientProgram getPatientProgram() {
        return patientProgram;
    }

    public void setPatientProgram(MdrtbPatientProgram patientProgram) {
        this.patientProgram = patientProgram;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getWrapperNames() {
        wrapperNames = this.patientProgram.getPatient().getGivenName() +" "+ this.patientProgram.getPatient().getFamilyName();
        if (StringUtils.isNotEmpty(this.patientProgram.getPatient().getMiddleName())){
            wrapperNames += " " + this.patientProgram.getPatient().getMiddleName();
        }

        return wrapperNames;
    }

    public void setWrapperNames(String wrapperNames) {
        this.wrapperNames = wrapperNames;
    }

    public void setFormartedVisitDate(String formartedVisitDate) {
        this.formartedVisitDate = formartedVisitDate;
    }

    public String getWrapperStatus() {
        if (this.patientProgram.getActive()){
            wrapperStatus = this.patientProgram.getPatientProgram().getProgram().getName();
        }
        else{
            PatientProgramDetails ppd = Context.getService(MdrtbDashboardService.class).getPatientProgramDetails(this.patientProgram.getPatientProgram());
            if (ppd.getOutcome() != null){
                wrapperStatus = ppd.getOutcome().getDisplayString();
            }
        }

        return wrapperStatus;
    }

    public void setWrapperStatus(String wrapperStatus) {
        this.wrapperStatus = wrapperStatus;
    }

    public Integer getWrapperLocationId() {
        return wrapperLocationId;
    }

    public void setWrapperLocationId(Integer wrapperLocationId) {
        this.wrapperLocationId = wrapperLocationId;
    }

    public String getWrapperLocationName() {
        return wrapperLocationName;
    }

    public void setWrapperLocationName(String wrapperLocationName) {
        this.wrapperLocationName = wrapperLocationName;
    }

    public String getWrapperAddress() {
        this.wrapperAddress = patientProgram.getPatient().getPersonAddress().getAddress1();
        return  this.wrapperAddress;
    }

    public void setWrapperAddress(String wrapperAddress) {
        this.wrapperAddress = wrapperAddress;
    }

    public PatientProgramDetails getPatientDetails() {
        return patientDetails;
    }

    public void setPatientDetails(PatientProgramDetails patientDetails) {
        this.patientDetails = patientDetails;
    }

    public String getWrapperRegisterDate() {
        this.wrapperRegisterDate = returnFormattedDate(patientProgram.getDateEnrolled());
        return wrapperRegisterDate;
    }

    public void setWrapperRegisterDate(String wrapperRegisterDate) {
        this.wrapperRegisterDate = wrapperRegisterDate;
    }

    public String getWrapperTreatmentDate() {
        PatientProgramDetails ppd = dashboardService.getPatientProgramDetails(patientProgram.getPatientProgram());
        List<PatientProgramRegimen> list = dashboardService.getPatientProgramRegimens(ppd,false);

        if (!list.isEmpty()){
            wrapperTreatmentDate = returnFormattedDate(list.get(0).getStartedOn());
        }

        return wrapperTreatmentDate;
    }

    public void setWrapperTreatmentDate(String wrapperTreatmentDate) {
        this.wrapperTreatmentDate = wrapperTreatmentDate;
    }

    public String getWrapperArt() {
        return wrapperArt;
    }

    public void setWrapperArt(String wrapperArt) {
        this.wrapperArt = wrapperArt;
    }

    public String getWrapperCpt() {
        return wrapperCpt;
    }

    public void setWrapperCpt(String wrapperCpt) {
        this.wrapperCpt = wrapperCpt;
    }

    public String getWrapperOutcome() {
        return wrapperOutcome;
    }

    public void setWrapperOutcome(String wrapperOutcome) {
        this.wrapperOutcome = wrapperOutcome;
    }

    public String getWrapperCompletedDate() {
        return wrapperCompletedDate;
    }

    public void setWrapperCompletedDate(String wrapperCompletedDate) {
        this.wrapperCompletedDate = wrapperCompletedDate;
    }

    public String getWrapperSecondLineDate() {
        return wrapperSecondLineDate;
    }

    public void setWrapperSecondLineDate(String wrapperSecondLineDate) {
        this.wrapperSecondLineDate = wrapperSecondLineDate;
    }

    public String getWrapperReason() {
        return wrapperReason;
    }

    public void setWrapperReason(String wrapperReason) {
        this.wrapperReason = wrapperReason;
    }

}
