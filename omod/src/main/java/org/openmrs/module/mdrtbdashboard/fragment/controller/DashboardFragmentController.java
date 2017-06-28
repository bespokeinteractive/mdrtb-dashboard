package org.openmrs.module.mdrtbdashboard.fragment.controller;

import net.sourceforge.jtds.jdbc.DateTime;
import org.openmrs.*;

import java.text.ParsePosition;
import java.util.*;

import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.util.DrugSensitivityModel;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.module.mdrtbdashboard.model.LocationFacilities;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.VisitDetails;
import org.openmrs.module.mdrtbdashboard.util.DrugTestingResults;
import org.openmrs.module.mdrtbdashboard.util.LocationModel;
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
    ProgramWorkflowService workflowService = Context.getProgramWorkflowService();
    MdrtbDashboardService dashboardService = Context.getService(MdrtbDashboardService.class);
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public String generateBMUNumber(@RequestParam(value = "regdate", required = false) Date regdate,
                                     UiSessionContext session){
        return  generateTbmuNumber(regdate, session.getSessionLocation());
    }

    public String generateTbmuNumber(Date regdate,
                                     Location location){
        LocationCentres centre = dashboardService.getCentresByLocation(location);
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(regdate);

        String stringTbmnuNumber = centre.getSerialNumber().trim() +  "/" + sdf.format(calendar.getTime()).toString();
        Integer integerTbmnuNumber = dashboardService.getNextTbmuNumberCount(stringTbmnuNumber);

        return  stringTbmnuNumber + "/0"  + ((calendar.get(Calendar.MONTH)/3)+1) + "/" + String.format("%04d", integerTbmnuNumber);
    }

    public SimpleObject enrollPatient(@RequestParam(value = "programId") Program program,
                                      @RequestParam(value = "patientId") Patient patient,
                                      @RequestParam(value = "enrolledOn") Date enrolledOn,
                                      @RequestParam(value = "enrollmentNotes", required = false) String enrollmentNotes,
                                      @RequestParam(value = "previousTreatment", required = false) String previousTreatment,
                                      @RequestParam(value = "classification",required = false) String classification,
                                      @RequestParam(value = "patientType", required = false) String patientType,
                                      @RequestParam(value = "treatmentCategory", required = false) String treatmentCategory,
                                      UiSessionContext session)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        MdrtbPatientProgram mostRecentProgram = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        if (mostRecentProgram != null && mostRecentProgram.getActive()){
            return SimpleObject.create("status", "failed", "message", "Patient already enrolled in Program!");
        }

        this.patientEnrollment(program, patient, enrolledOn, previousTreatment, classification, patientType, treatmentCategory, session);

        // Return Object for Success
        return SimpleObject.create("status", "success", "message", "Patient successfully enrolled!");
    }

    public void patientEnrollment(Program program,
                                  Patient patient,
                                  Date enrolledOn,
                                  String previousTreatment,
                                  String classification,
                                  String patientType,
                                  String treatmentCategory,
                                  UiSessionContext session){
        PatientProgramDetails details = new PatientProgramDetails();
        MdrtbPatientProgram mpp = new MdrtbPatientProgram(program);
        Location location = session.getSessionLocation();

        // set the patient program parameters
        mpp.setPatient(patient);
        mpp.setLocation(location);
        mpp.setDateEnrolled(enrolledOn);

        if (program.getName().equals(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name"))){
            // set program workflows if the Patient is an MDR-TB Patient
            mpp.setClassificationAccordingToPreviousDrugUse(workflowService.getStateByUuid(classification));
            mpp.setClassificationAccordingToPreviousTreatment(workflowService.getStateByUuid(previousTreatment));

            details.setPatientType(mpp.getClassificationAccordingToPreviousDrugUse());
            details.setPatientCategory(mpp.getClassificationAccordingToPreviousTreatment());
        }
        else {
            // set program workflows if the Patient is an TB Patient
            mpp.setClassificationAccordingToPatientType(workflowService.getStateByUuid(patientType));
            mpp.setClassificationAccordingToTreatmentCategory(workflowService.getStateByUuid(treatmentCategory));

            details.setPatientType(mpp.getClassificationAccordingToPatientType());
            details.setPatientCategory(mpp.getClassificationAccordingToTreatmentCategory());
        }

        // save the actual update
        PatientProgram pp = workflowService.savePatientProgram(mpp.getPatientProgram());

        // set the patient program details parameters & save
        details.setTbmuNumber(generateTbmuNumber(enrolledOn, location));
        details.setPatientProgram(pp);
        dashboardService.savePatientProgramDetails(details);
    }

    public SimpleObject transferPatient(@RequestParam(value = "patientId") Patient patient,
                                        @RequestParam(value = "enrolledOn") Date enrolledOn,
                                        @RequestParam(value = "previousTreatment", required = false) String previousTreatment,
                                        @RequestParam(value = "classification",required = false) String classification,
                                        @RequestParam(value = "patientType", required = false) String patientType,
                                        @RequestParam(value = "treatmentCategory", required = false) String treatmentCategory,
                                        UiSessionContext session,
                                        SessionStatus status)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        MdrtbPatientProgram current = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        Location location = session.getSessionLocation();

        if (mdrtbService.getPersonLocation(patient).getLocation().equals(location)){
            return SimpleObject.create("status", "failed", "message", "You can't transfer to the same Location!");
        }

        if (current.getDateCompleted() != null){
            //Close previous Entry
            Date completedOn = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(enrolledOn);
            cal.add(Calendar.DATE, -1);
            completedOn = cal.getTime();

            current.setDateCompleted(completedOn);
            current.setOutcome(workflowService.getStateByUuid("341a7f5a-0370-102d-b0e3-001ec94a0cc1"));
            workflowService.savePatientProgram(current.getPatientProgram());
        }

        this.patientEnrollment(current.getPatientProgram().getProgram(), patient, enrolledOn, previousTreatment, classification, patientType, treatmentCategory, session);

        // Return Object for Success
        return SimpleObject.create("status", "success", "message", "Patient successfully transferred!");
    }

    public String getSelectedLocation(UiSessionContext session){
        return session.getSessionLocation().getName();
    }

    public SimpleObject getSelectedLocationFacilities(UiSessionContext session){
        Location location = session.getSessionLocation();
        List<LocationFacilities> facilities = dashboardService.getFacilities(location, "active");
        List<LocationModel> models = new ArrayList<LocationModel>();

        for (LocationFacilities facility: facilities){
            LocationModel lm = new LocationModel();
            lm.setId(facility.getId());
            lm.setName(facility.getName());

            models.add(lm);
        }

        return SimpleObject.create("facilities", models);
    }

    public SimpleObject getVisitSummaryDetails(@RequestParam("encounterId") Integer encounterId,
                                               UiUtils ui) {
        Encounter encounter = Context.getEncounterService().getEncounter(encounterId);
        VisitDetails visitDetail = VisitDetails.create(encounter);
        SimpleObject details = SimpleObject.fromObject(visitDetail, ui, "weight", "height", "bmi", "muac", "facility", "date", "location", "sputumSmear", "genXpert", "hivExam", "xrayExam", "culture", "drugTest", "artStarted", "cptStarted", "showTests", "labNumber");
        List<DrugTestingResults> drugTest = dashboardService.getDrugSensitivityOutcome(encounter);

        return SimpleObject.create("details", details, "drugTest", drugTest);
    }

}

