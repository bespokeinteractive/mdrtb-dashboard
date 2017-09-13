package org.openmrs.module.mdrtbdashboard;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramTransfers;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by Dennis Henry
 * Created on 9/13/2017.
 */
public class MdrtbTransferWrapper {
    private PatientProgramTransfers patientTransfers;
    private PatientProgramDetails patientDetails;
    private String wrapperIdentifier = "N/A";
    private String wrapperNames = "N/A";
    private String wrapperDated = "N/A";

    public MdrtbTransferWrapper(PatientProgramTransfers patientTransfers){
        this.patientTransfers = patientTransfers;

        this.patientDetails = Context.getService(MdrtbDashboardService.class).getPatientProgramDetails(patientTransfers.getPatientProgram());
        this.wrapperIdentifier = patientDetails.getTbmuNumber();
    }

    public PatientProgramTransfers getPatientTransfers() {
        return patientTransfers;
    }

    public void setPatientTransfers(PatientProgramTransfers patientTransfers) {
        this.patientTransfers = patientTransfers;
    }

    public String getWrapperIdentifier() {
        return wrapperIdentifier;
    }

    public void setWrapperIdentifier(String wrapperIdentifier) {
        this.wrapperIdentifier = wrapperIdentifier;
    }

    public String getWrapperNames() {
        wrapperNames = this.patientTransfers.getPatientProgram().getPatient().getGivenName() +" "+ this.patientTransfers.getPatientProgram().getPatient().getFamilyName();
        if (StringUtils.isNotEmpty(this.patientTransfers.getPatientProgram().getPatient().getMiddleName())){
            wrapperNames += " " + this.patientTransfers.getPatientProgram().getPatient().getMiddleName();
        }

        return wrapperNames;
    }

    public void setWrapperNames(String wrapperNames) {
        this.wrapperNames = wrapperNames;
    }

    public PatientProgramDetails getPatientDetails() {
        return patientDetails;
    }

    public void setPatientDetails(PatientProgramDetails patientDetails) {
        this.patientDetails = patientDetails;
    }

    public String getWrapperDated() {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.wrapperDated = formatter.format(patientTransfers.getTransferDate());

        return wrapperDated;
    }

    public void setWrapperDated(String wrapperDated) {
        this.wrapperDated = wrapperDated;
    }
}
