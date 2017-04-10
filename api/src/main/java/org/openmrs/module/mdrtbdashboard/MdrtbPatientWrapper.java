package org.openmrs.module.mdrtbdashboard;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dennis Henry on 2/2/2017.
 */
public class MdrtbPatientWrapper {
    private MdrtbPatientProgram patientProgram;
    private String wrapperIdentifier = "N/A";
    private String wrapperNames = "N/A";
    private String wrapperStatus = "Completed";
    private String formartedVisitDate;
    private Date visitDate = new Date();

    private static final Logger log = Logger.getLogger(MdrtbPatientWrapper.class.getName());

    public MdrtbPatientWrapper(MdrtbPatientProgram patientProgram){
        this.patientProgram = patientProgram;

        try{
            this.wrapperIdentifier = Context.getService(MdrtbDashboardService.class).getPatientProgramDetails(patientProgram.getPatientProgram()).getTbmuNumber();
        }
        catch (Exception e){ }
    }

    public MdrtbPatientWrapper(MdrtbPatientProgram patientProgram, Date visitDate){
        this.patientProgram = patientProgram;
        this.visitDate = visitDate;
        try {
            this.wrapperIdentifier = Context.getService(MdrtbDashboardService.class).getPatientProgramDetails(patientProgram.getPatientProgram()).getTbmuNumber();
        }
        catch (Exception e){ }
    }

    public String getWrapperIdentifier() {
        return wrapperIdentifier;
    }

    public void setWrapperIdentifier(String wrapperIdentifier) {
        this.wrapperIdentifier = wrapperIdentifier;
    }

    public String getFormartedVisitDate() {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        try{
            formartedVisitDate = formatter.format(visitDate);
        }catch(Exception e){
            if(visitDate!=null){
                formartedVisitDate = visitDate.toString();
            }     else{
                formartedVisitDate = "N/A";
            }
            log.log( Level.SEVERE, e.toString(), e );
        }

        return formartedVisitDate;
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
        return wrapperStatus;
    }

    public void setWrapperStatus(String wrapperStatus) {
        this.wrapperStatus = wrapperStatus;
    }

}
