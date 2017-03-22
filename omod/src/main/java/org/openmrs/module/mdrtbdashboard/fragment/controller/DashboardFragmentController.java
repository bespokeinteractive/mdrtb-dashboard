package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.*;
import java.util.*;

import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.util.DrugSensitivityModel;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.VisitDetails;
import org.openmrs.module.mdrtbdashboard.util.DrugTestingResults;
import org.openmrs.ui.framework.SimpleObject;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

import org.openmrs.ui.framework.UiUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Created by Dennis Henry
 * Created on 12/23/2016.
 */

public class DashboardFragmentController {
    public String generateBMUNumber(@RequestParam(value = "regdate", required = false) Date regdate,
                                     UiSessionContext session){
        return  generateTbmuNumber(regdate, session.getSessionLocation());
    }

    public String generateTbmuNumber(Date regdate,
                                    Location location){
        LocationCentres centre = Context.getService(MdrtbDashboardService.class).getCentresByLocation(location);

        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(regdate);

        String stringTbmnuNumber = centre.getSerialNumber().trim() +"/0" + ((calendar.get(Calendar.MONTH)/3)+1) +  "/" + sdf.format(calendar.getTime()).toString() + "/";
        Integer integerTbmnuNumber = Context.getService(MdrtbDashboardService.class).getNextTbmuNumberCount(stringTbmnuNumber);

        return  stringTbmnuNumber + String.format("%04d", integerTbmnuNumber);
    }

    public SimpleObject enrollPatient(@RequestParam(value = "programId") Program program,
                                      @RequestParam(value = "patientId") Patient patient,
                                      @RequestParam(value = "enrolledOn") Date enrolledOn,
                                      @RequestParam(value = "enrollmentNotes", required = false) String enrollmentNotes,
                                      @RequestParam(value = "previousTreatment", required = false) String previousTreatment,
                                      @RequestParam(value = "classification",required = false) String classification,
                                      @RequestParam(value = "patientType", required = false) String patientType,
                                      @RequestParam(value = "treatmentCategory", required = false) String treatmentCategory,
                                      UiSessionContext session,
                                      SessionStatus status)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        MdrtbPatientProgram mostRecentProgram = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
        if (mostRecentProgram != null && mostRecentProgram.getActive()){
            return SimpleObject.create("status", "failed", "message", "Patient already enrolled in Program!");
        }

        MdrtbPatientProgram mpp = new MdrtbPatientProgram(program);
        Location location = session.getSessionLocation();

        // set the patient program parameters
        mpp.setPatient(patient);
        mpp.setLocation(location);
        mpp.setDateEnrolled(enrolledOn);

        if (program.getName().equals(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name"))){
            // set program workflows if the Patient is an MDR-TB Patient
            mpp.setClassificationAccordingToPreviousDrugUse(Context.getProgramWorkflowService().getStateByUuid(classification));
            mpp.setClassificationAccordingToPreviousTreatment(Context.getProgramWorkflowService().getStateByUuid(previousTreatment));
        }
        else {
            // set program workflows if the Patient is an TB Patient
            mpp.setClassificationAccordingToPatientType(Context.getProgramWorkflowService().getStateByUuid(patientType));
            mpp.setClassificationAccordingToTreatmentCategory(Context.getProgramWorkflowService().getStateByUuid(treatmentCategory));
        }

        // save the actual update
        ProgramWorkflowService programWorkflowService = Context.getProgramWorkflowService();
        PatientProgram pp = programWorkflowService.savePatientProgram(mpp.getPatientProgram());

        // set the patient program details parameters
        PatientProgramDetails details = new PatientProgramDetails();
        details.setTbmuNumber(generateTbmuNumber(enrolledOn, location));
        details.setPatientProgram(pp);
        details.setLabNumber("x");
        details.setDetails(enrollmentNotes);

        // save the the patient program details
        MdrtbDashboardService mdrtbservice = Context.getService(MdrtbDashboardService.class);
        mdrtbservice.savePatientProgramDetails(details);

        //status.setComplete();

        // Return Object for Success
        return SimpleObject.create("status", "success", "message", "Patient successfully enrolled!");
    }

    public String getSelectedLocation(UiSessionContext session){
        return session.getSessionLocation().getName();
    }

    public SimpleObject getVisitSummaryDetails(@RequestParam("encounterId") Integer encounterId,
                                               UiUtils ui) {
        Encounter encounter = Context.getEncounterService().getEncounter(encounterId);
        VisitDetails visitDetail = VisitDetails.create(encounter);
        SimpleObject details = SimpleObject.fromObject(visitDetail, ui, "weight", "height", "bmi", "muac", "facility", "date", "location", "sputumSmear", "genXpert", "hivExam", "xrayExam", "culture", "drugTest", "artStarted", "cptStarted", "showTests", "labNumber");
        List<DrugTestingResults> drugTest = Context.getService(MdrtbDashboardService.class).getDrugSensitivityOutcome(encounter);

        return SimpleObject.create("details", details, "drugTest", drugTest);
    }

}
