package org.openmrs.module.mdrtbdashboard;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.mdrtb.model.PatientProgramDetails;

/**
 * Created by Dennys Henry
 * Created on 10/27/2017.
 */
public class MdrtbActiveWrapper {
    private PatientProgramDetails patientDetails;
    private String wrapperIdentifier = "N/A";
    private String wrapperNames = "N/A";

    public MdrtbActiveWrapper(PatientProgramDetails ppd){
        this.patientDetails = ppd;
    }

    public PatientProgramDetails getPatientDetails() {
        return patientDetails;
    }

    public void setPatientDetails(PatientProgramDetails patientDetails) {
        this.patientDetails = patientDetails;
    }

    public String getWrapperIdentifier() {
        this.wrapperIdentifier = patientDetails.getTbmuNumber();
        return wrapperIdentifier;
    }

    public void setWrapperIdentifier(String wrapperIdentifier) {
        this.wrapperIdentifier = wrapperIdentifier;
    }

    public String getWrapperNames() {
        wrapperNames = this.patientDetails.getPatientProgram().getPatient().getGivenName() +" "+ this.patientDetails.getPatientProgram().getPatient().getFamilyName();
        if (StringUtils.isNotEmpty(this.patientDetails.getPatientProgram().getPatient().getMiddleName())){
            wrapperNames += " " + this.patientDetails.getPatientProgram().getPatient().getMiddleName();
        }

        return wrapperNames.toUpperCase();
    }

    public void setWrapperNames(String wrapperNames) {
        this.wrapperNames = wrapperNames;
    }
}
