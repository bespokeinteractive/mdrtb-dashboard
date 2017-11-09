package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.*;

import java.text.ParsePosition;
import java.util.*;

import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.model.LocationCentres;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.*;
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
        LocationCentres centre = mdrtbService.getCentresByLocation(location);
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(regdate);

        String stringTbmnuNumber = centre.getSerialNumber().trim() +  "/" + sdf.format(calendar.getTime()).toString();
        Integer integerTbmnuNumber = dashboardService.getNextTbmuNumberCount(stringTbmnuNumber);

        return  stringTbmnuNumber + "/0"  + ((calendar.get(Calendar.MONTH)/3)+1) + "/" + String.format("%04d", integerTbmnuNumber);
    }

    public SimpleObject updatePatientRegimen(@RequestParam(value = "ppdId") Integer ppdId,
                                             @RequestParam(value = "type") Concept type,
                                             @RequestParam(value = "name") String name,
                                             @RequestParam(value = "start") String start,
                                             @RequestParam(value = "remarks", required = false) String remarks){
        PatientProgramDetails ppd = dashboardService.getPatientProgramDetails(ppdId);
        List<PatientProgramRegimen> regimens = dashboardService.getPatientProgramRegimens(ppd, true);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = df.parse(start, new ParsePosition(0));

        for (PatientProgramRegimen regimen : regimens){
            regimen.setFinishedOn(date);
            dashboardService.savePatientProgramRegimen(regimen);
        }

        if (StringUtils.isEmpty(remarks)){
            remarks = "N/A";
        }

        PatientProgramRegimen regimen = new PatientProgramRegimen();
        regimen.setProgramDetails(ppd);
        regimen.setType(type);
        regimen.setName(name);
        regimen.setStartedOn(date);
        regimen.setRemarks(remarks);
        regimen = dashboardService.savePatientProgramRegimen(regimen);

        ppd.setRegimen(regimen);
        dashboardService.savePatientProgramDetails(ppd);

        return SimpleObject.create("status", "success", "message", "Patient visit successfully updated!");
    }

    public String getRegimenName(@RequestParam(value = "concept") Concept concept,
                                 @RequestParam(value = "program") Program program){
        RegimentType regimentType = dashboardService.getRegimenType(concept, program);
        return regimentType.getName();
    }

    public List<SimpleObject> getRegimenNames(@RequestParam(value = "concept") Concept concept,
                                              @RequestParam(value = "program") Program program,
                                              UiUtils ui){
        List<RegimentType> types = dashboardService.getRegimenTypes(concept, program);
        return SimpleObject.fromCollection(types, ui,"id", "name");
    }

    public SimpleObject enrollPatient(@RequestParam(value = "programId") Program program,
                                      @RequestParam(value = "patientId") Patient patient,
                                      @RequestParam(value = "enrolledOn") Date enrolledOn,
                                      @RequestParam(value = "treatmentSite") Concept site,
                                      @RequestParam(value = "confirmationSite") Concept confirmation,
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

        PatientProgramDetails ppd = this.patientEnrollment(program, patient, enrolledOn, previousTreatment, classification, patientType, treatmentCategory, null, site, confirmation, session);

        // Return Object for Success
        return SimpleObject.create("status", "success", "message", "Patient successfully enrolled!", "programId", ppd.getId());
    }

    public SimpleObject getPatientProgramDetails(@RequestParam(value = "programId") PatientProgram pp){
        PatientProgramDetails ppd = dashboardService.getPatientProgramDetails(pp);
        String tbmu  = "N/A";

        if (ppd != null){
            tbmu = ppd.getTbmuNumber();
        }

        String names = pp.getPatient().getGivenName() + " " + pp.getPatient().getFamilyName();
        if (pp.getPatient().getMiddleName() != null){
            names += " " + pp.getPatient().getMiddleName();
        }

        return SimpleObject.create("names", names, "identifier", tbmu);
    }

    public SimpleObject voidPatient(@RequestParam(value = "programId") PatientProgram pp,
                                    @RequestParam(value = "reasons") String reasons,
                                    UiSessionContext session){
        pp.setVoided(true);
        pp.setVoidedBy(session.getCurrentUser());
        pp.setDateVoided(new Date());
        pp.setVoidReason(reasons);

        workflowService.savePatientProgram(pp);

        return SimpleObject.create("status", "success", "message", "Patient successfully voided!", "programId", pp.getPatientProgramId());
    }

    public PatientProgramDetails patientEnrollment(Program program,
                                                   Patient patient,
                                                   Date enrolledOn,
                                                   String previousTreatment,
                                                   String classification,
                                                   String patientType,
                                                   String treatmentCategory,
                                                   PatientProgram referredFrom,
                                                   Concept site,
                                                   Concept confirmation,
                                                   UiSessionContext session){
        PatientProgramDetails ppd = new PatientProgramDetails();
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

            ppd.setPatientType(mpp.getClassificationAccordingToPreviousDrugUse());
            ppd.setPatientCategory(mpp.getClassificationAccordingToPreviousTreatment());
        }
        else {
            // set program workflows if the Patient is an TB Patient
            mpp.setClassificationAccordingToPatientType(workflowService.getStateByUuid(patientType));
            mpp.setClassificationAccordingToTreatmentCategory(workflowService.getStateByUuid(treatmentCategory));

            ppd.setPatientType(mpp.getClassificationAccordingToPatientType());
            ppd.setPatientCategory(mpp.getClassificationAccordingToTreatmentCategory());
        }

        if (referredFrom != null){
            ppd.setReferringProgram(referredFrom);
        }

        // save the actual update
        PatientProgram pp = workflowService.savePatientProgram(mpp.getPatientProgram());

        // set the patient program details parameters & save
        ppd.setTbmuNumber(generateTbmuNumber(enrolledOn, location));
        ppd.setPatientProgram(pp);
        ppd.setDiseaseSite(site);
        ppd.setConfirmationSite(confirmation);

        return dashboardService.savePatientProgramDetails(ppd);
    }

    public SimpleObject transferPatient(@RequestParam(value = "patientId") Patient patient,
                                        @RequestParam(value = "programId") PatientProgram program,
                                        @RequestParam(value = "enrolledOn") Date enrolledOn,
                                        @RequestParam(value = "treatmentSite") Concept site,
                                        @RequestParam(value = "confirmationSite") Concept confirmation,
                                        @RequestParam(value = "previousTreatment", required = false) String previousTreatment,
                                        @RequestParam(value = "classification",required = false) String classification,
                                        @RequestParam(value = "patientType", required = false) String patientType,
                                        @RequestParam(value = "treatmentCategory", required = false) String treatmentCategory,
                                        UiSessionContext session,
                                        SessionStatus status)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        MdrtbPatientProgram mpp = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        PatientProgramDetails ppd = dashboardService.getPatientProgramDetails(mpp);

        PatientProgramDetails pd = dashboardService.getPatientProgramDetails(mpp);
        PatientProgram ref = new PatientProgram();
        Location location = session.getSessionLocation();

        if (mpp.getPatientProgram().getLocation().equals(location)){
            return SimpleObject.create("status", "failed", "message", "You can't transfer to the same Location!");
        }

        if (mpp.getDateCompleted() == null){
            Date completedOn;
            Calendar cal = Calendar.getInstance();
            cal.setTime(enrolledOn);
            cal.add(Calendar.DATE, -1);
            completedOn = cal.getTime();

            PatientProgram pp = mpp.getPatientProgram();
            pp.setDateCompleted(completedOn);
            pp.setOutcome(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PATIENT_TRANSFERRED_OUT));
            pp = Context.getProgramWorkflowService().savePatientProgram(pp);

            ppd.setTransferred(true);
            ppd.setOutcome(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PATIENT_TRANSFERRED_OUT));
            dashboardService.savePatientProgramDetails(ppd);

            ref = pp;
            this.closeTransferIns(ref);
        }

        if (program != null){
            ref = program;
            this.closeTransferIns(ref);
        }

        ppd = this.patientEnrollment(mpp.getPatientProgram().getProgram(), patient, enrolledOn, previousTreatment, classification, patientType, treatmentCategory, ref, site, confirmation,  session);

        // Return Object for Success
        return SimpleObject.create("status", "success", "programId", ppd.getPatientProgram().getPatientProgramId(), "message", "Patient successfully transferred!");
    }

    private void closeTransferIns(PatientProgram pp){
        List<PatientProgramTransfers> transfers = dashboardService.getActivePatientTransfers(pp);
        for (PatientProgramTransfers transfer : transfers){
            transfer.setProcessed(true);
            this.dashboardService.savePatientProgramTransfers(transfer);
        }
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

    public Integer getTransfersCount(UiSessionContext session){
        List<PatientProgramTransfers> list = dashboardService.getPatientProgramTransfers(session.getSessionLocation(), false);
        return list.size();
    }
}

