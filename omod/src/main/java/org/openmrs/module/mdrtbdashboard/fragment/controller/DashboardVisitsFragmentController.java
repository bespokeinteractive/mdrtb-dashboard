package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.form.SimpleFollowUpForm;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.util.DrugSensitivityModel;
import org.openmrs.module.mdrtbdashboard.VisitDetailsWrapper;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramTransfers;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramVisits;
import org.openmrs.module.mdrtbdashboard.model.VisitTypes;
import org.openmrs.module.mdrtbdashboard.util.ResultModelWrapper;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Dennis Henry
 * Created on 1/31/2017.
 */
public class DashboardVisitsFragmentController {
    MdrtbDashboardService dashboardService = Context.getService(MdrtbDashboardService.class);

    public void controller(FragmentModel model,
                           FragmentConfiguration config,
                           UiUtils ui) {
        config.require("patientId");
        Patient patient = Context.getPatientService().getPatient(Integer.parseInt(config.get("patientId").toString()));
        MdrtbPatientProgram mpp = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);

        List<Encounter> visitSummaries = Context.getEncounterService().getEncounters(patient, null, mpp.getDateEnrolled(), mpp.getDateCompleted(), null, null, false);
        List<VisitDetailsWrapper> wrapperList = getVisitDetailsWrappers(visitSummaries);

        Collection<ConceptAnswer> smearResults = Context.getService(MdrtbService.class).getPossibleSmearResults();
        Collection<ConceptAnswer> genXpertResults = Context.getService(MdrtbService.class).getPossibleGenXpertResults();
        Collection<ConceptAnswer> cultureResults = Context.getService(MdrtbService.class).getPossibleCultureResults();

        List<Concept> dstDrugs = Context.getService(MdrtbService.class).getDstDrugs();

        Collections.reverse(visitSummaries);
        Collections.reverse(wrapperList);

        model.addAttribute("visitSummaries", visitSummaries);
        model.addAttribute("wrapperList", wrapperList);
        model.addAttribute("dstDrugs", dstDrugs);
        model.addAttribute("smearResults", smearResults);
        model.addAttribute("cultureResults", cultureResults);
        model.addAttribute("genXpertResults", genXpertResults);
    }

    public SimpleObject addPatientVisit(@RequestParam(value = "patientId") Patient patient,
                                        @RequestParam(value = "programId") Integer programId,
                                        @RequestParam(value = "labNumber") String labNumber,
                                        @RequestParam(value = "testedOn") String testedOn,
                                        @RequestParam(value = "testResult") String result,
                                        @RequestParam(value = "transferTo") Location transferTo,
                                        @RequestParam(value = "outcomeResults") String outcomeResults,
                                        @RequestParam(value = "outcomeRemarks", required = false) String outcomeRemarks,
                                        UiSessionContext session)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        MdrtbPatientProgram mpp = Context.getService(MdrtbService.class).getMdrtbPatientProgram(programId);
        List <VisitTypes> vt = dashboardService.getVisitTypes(mpp.getPatientProgram().getProgram(), false,true, false);

        PatientProgramDetails ppd = dashboardService.getPatientProgramDetails(mpp);
        PatientProgramVisits ppv = dashboardService.getPatientProgramVisit(mpp.getPatientProgram(), vt.get(0));
        if (ppv == null){
            ppv = new PatientProgramVisits();
            ppv.setPatientProgram(mpp.getPatientProgram());
            ppv.setVisitType(vt.get(0));
        }


        SimpleFollowUpForm followup = new SimpleFollowUpForm(patient);
        //Set up Encounter
        followup.setLocation(session.getSessionLocation());
        followup.setProvider(Context.getPersonService().getPerson(3));
        followup.setEncounterDatetime(new Date());

        //Store Sputum Smear Result
        followup.setSputumSmear(testedOn, labNumber, result);

        //Save Encounter
        Encounter encounter = Context.getEncounterService().saveEncounter(followup.getEncounter());

        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = df.parse(testedOn, new ParsePosition(0));
        Concept outcome = Context.getConceptService().getConcept(Integer.parseInt(outcomeResults));

        PatientProgram pp = mpp.getPatientProgram();
        pp.setDateCompleted(date);
        pp.setOutcome(outcome);
        ppd.setOutcome(outcome);

        ppv.setSputumResult(Context.getConceptService().getConcept(Integer.parseInt(result)));
        ppv.setExamDate(date);
        ppv.setEncounter(encounter);
        ppv.setLabNumber(labNumber);

        if (outcome.equals(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DIED))){
            patient.setDead(true);
            patient.setDeathDate(date);
            patient.setCauseOfDeath(pp.getProgram().getConcept());
            Context.getPatientService().savePatient(patient);
        }

        if (transferTo != null){
            PatientProgramTransfers ppt = new PatientProgramTransfers();
            ppt.setLocation(transferTo);
            ppt.setPatientProgram(pp);
            ppt.setTransferDate(date);

            this.dashboardService.savePatientProgramTransfers(ppt);
        }

        Context.getProgramWorkflowService().savePatientProgram(pp);

        this.dashboardService.savePatientProgramVisits(ppv);
        this.dashboardService.savePatientProgramDetails(ppd);
        this.dashboardService.saveParentProgramOutcome(ppd, outcome, date);

        //Return Answer
        return SimpleObject.create("status", "success", "message", "Patient visit successfully updated!");
    }

    public SimpleObject updateGenXpert (@RequestParam(value = "patientId") Patient patient,
                                        @RequestParam(value = "labNumber") String labNumber,
                                        @RequestParam(value = "testedOn") Date testedOn,
                                        @RequestParam(value = "testResult") String result,
                                        UiSessionContext session,
                                        SessionStatus status)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SimpleFollowUpForm followup = new SimpleFollowUpForm(patient);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testedOn);
        calendar.add(Calendar.DATE, 1);

        List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, testedOn, calendar.getTime(), null, null, null, null, null, false);
        for (Encounter encounter : encounters){
            if (encounter.getEncounterType().equals(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.follow_up_encounter_type")))){
                followup = new SimpleFollowUpForm(encounter);
                break;
            }
        }

        //Set up Encounter
        followup.setLocation(session.getSessionLocation());
        followup.setProvider(Context.getPersonService().getPerson(3));
        followup.setEncounterDatetime(new Date());
        followup.setLabNumber(labNumber);

        //Store GenXpert Result
        followup.setGenXpert(df.format(testedOn), result);

        //Save Encounter
        Encounter encounter = Context.getEncounterService().saveEncounter(followup.getEncounter());

        //Return Answer
        return SimpleObject.create("status", "success", "message", "GenXpert successfully updated!");
    }

    public SimpleObject updateCulture (@RequestParam(value = "patientId") Patient patient,
                                        @RequestParam(value = "labNumber") String labNumber,
                                        @RequestParam(value = "testedOn") Date testedOn,
                                        @RequestParam(value = "testResult") String result,
                                        UiSessionContext session,
                                        SessionStatus status)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SimpleFollowUpForm followup = new SimpleFollowUpForm(patient);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testedOn);
        calendar.add(Calendar.DATE, 1);

        List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, testedOn, calendar.getTime(), null, null, null, null, null, false);
        for (Encounter encounter : encounters){
            if (encounter.getEncounterType().equals(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.follow_up_encounter_type")))){
                followup = new SimpleFollowUpForm(encounter);
                break;
            }
        }

        //Set up Encounter
        followup.setLocation(session.getSessionLocation());
        followup.setProvider(Context.getPersonService().getPerson(3));
        followup.setEncounterDatetime(new Date());
        followup.setLabNumber(labNumber);

        //Store Culture Result
        followup.setCultureResults(df.format(testedOn), result);

        //Save Encounter
        Encounter encounter = Context.getEncounterService().saveEncounter(followup.getEncounter());

        //Return Answer
        return SimpleObject.create("status", "success", "message", "Culture successfully updated!");
    }

    public SimpleObject updateDrugSusceptibilityTests(@BindParams("wrap") ResultModelWrapper wrapper,
                                                      HttpServletRequest request,
                                                      UiSessionContext session)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        String labNumber = wrapper.getLabNumber();
        Date testedOn = wrapper.getTestDate();
        Patient patient = wrapper.getPatient();

        List <DrugSensitivityModel> results = new ArrayList<DrugSensitivityModel>();

        for (Map.Entry<String, String[]> params : ((Map<String, String[]>) request.getParameterMap()).entrySet()) {
            if (StringUtils.contains(params.getKey(), "results.")){
                DrugSensitivityModel rm = new DrugSensitivityModel();
                rm.setDrug(Context.getConceptService().getConcept(Integer.parseInt(params.getKey().substring("results.".length()) )));
                rm.setResult(Context.getConceptService().getConcept(Integer.parseInt(params.getValue()[0])));
                results.add(rm);
            }
        }

        SimpleFollowUpForm followup = new SimpleFollowUpForm(patient);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testedOn);
        calendar.add(Calendar.DATE, 1);

        List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, testedOn, calendar.getTime(), null, null, null, null, null, false);
        for (Encounter encounter : encounters){
            if (encounter.getEncounterType().equals(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.follow_up_encounter_type")))){
                followup = new SimpleFollowUpForm(encounter);
                break;
            }
        }

        //Set up Encounter
        followup.setLocation(session.getSessionLocation());
        followup.setProvider(Context.getPersonService().getPerson(3));
        followup.setEncounterDatetime(new Date());
        followup.setLabNumber(labNumber);

        //Store Drug Sensitivity Exam Results
        followup.setDrugSusceptibilityTests(df.format(testedOn), results);

        //Save Encounter
        Encounter encounter = Context.getEncounterService().saveEncounter(followup.getEncounter());

        //Return Answer
        return SimpleObject.create("status", "success", "message", "DST successfully updated!");
    }

    public SimpleObject exitMdrtbPatients(@RequestParam(value = "patientId") Patient patient,
                                          @RequestParam(value = "programId") Integer programId,
                                          @RequestParam(value = "outcomeDate") Date outcomeDate,
                                          @RequestParam(value = "outcomeResult") String outcomeResults,
                                          @RequestParam(value = "outcomeRemarks", required = false) String outcomeRemarks,
                                          UiSessionContext session,
                                          SessionStatus status)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        MdrtbPatientProgram mpp = Context.getService(MdrtbService.class).getMdrtbPatientProgram(programId);
        PatientProgramDetails pd = dashboardService.getPatientProgramDetails(mpp.getPatientProgram());
        PatientProgram pp = mpp.getPatientProgram();
        Concept outcome = Context.getConceptService().getConcept(Integer.parseInt(outcomeResults));

        pp.setDateCompleted(outcomeDate);
        pp.setOutcome(outcome);
        pd.setOutcome(outcome);

        if (outcome.equals(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DIED))){
            patient.setDead(true);
            Context.getPatientService().savePatient(patient);
        }

        //Save Patient Details
        dashboardService.savePatientProgramDetails(pd);
        dashboardService.saveParentProgramOutcome(pd, outcome, outcomeDate);

        //Save the actual update
        Context.getProgramWorkflowService().savePatientProgram(pp);

        //Return Answer
        return SimpleObject.create("status", "success", "message", "Patient visit successfully updated!");
    }

    public String getObsLabNumber(@RequestParam(value = "patientId") Patient patient,
                                  @RequestParam(value = "date") Date date,
                                  SessionStatus status)
                                  throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String labNumber = "";

        List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, date, calendar.getTime(), null, null, null, null, null, false);
        outerLoop:
        for (Encounter encounter : encounters){
            if (encounter.getEncounterType().equals(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.follow_up_encounter_type")))){
                for (Obs obs :encounter.getAllObs()) {
                    if (obs.getConcept().equals(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LAB_TEST_SERIAL_NUMBER))) {
                        labNumber = obs.getValueText();
                        break outerLoop;
                    }
                }
            }
        }

        return labNumber;
    }

    private List<VisitDetailsWrapper> getVisitDetailsWrappers(List<Encounter> encounters){
        List<VisitDetailsWrapper> wrappers = new ArrayList<VisitDetailsWrapper>();
        for (Encounter encounter: encounters){
            VisitDetailsWrapper vw = new VisitDetailsWrapper(encounter);
            wrappers.add(vw);
        }

        return wrappers;
    }

}
