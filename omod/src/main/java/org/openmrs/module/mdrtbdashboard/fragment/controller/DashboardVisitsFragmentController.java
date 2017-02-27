package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.form.SimpleFollowUpForm;
import org.openmrs.module.mdrtb.form.SimpleIntakeForm;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Dennis Henry on 1/31/2017.
 */
public class DashboardVisitsFragmentController {
    public void controller(FragmentModel model,
                           FragmentConfiguration config,
                           UiUtils ui) {
        config.require("patientId");
        Patient patient = Context.getPatientService().getPatient(Integer.parseInt(config.get("patientId").toString()));
        MdrtbPatientProgram mpp = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);

        List<Encounter> visitSummaries = Context.getEncounterService().getEncounters(patient, null, mpp.getDateEnrolled(), mpp.getDateCompleted(), null, null, false);
        Collection<ConceptAnswer> smearResults = Context.getService(MdrtbService.class).getPossibleSmearResults();
        Collection<ConceptAnswer> genXpertResults = Context.getService(MdrtbService.class).getPossibleGenXpertResults();

        Collections.reverse(visitSummaries);

        model.addAttribute("visitSummaries", visitSummaries);
        model.addAttribute("smearResults", smearResults);
        model.addAttribute("genXpertResults", genXpertResults);
    }

    public SimpleObject addPatientVisit(@RequestParam(value = "patientId") Patient patient,
                                        @RequestParam(value = "programId") Integer programId,
                                        @RequestParam(value = "labNumber") String labNumber,
                                        @RequestParam(value = "testedOn") String testedOn,
                                        @RequestParam(value = "testResult") String result,
                                        @RequestParam(value = "outcomeResults", required = false) String outcomeResults,
                                        @RequestParam(value = "outcomeRemarks", required = false) String outcomeRemarks,
                                        UiSessionContext session,
                                        SessionStatus status)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SimpleFollowUpForm followup = new SimpleFollowUpForm(patient);
        //Set up Encounter
        followup.setLocation(session.getSessionLocation());
        followup.setProvider(Context.getPersonService().getPerson(3));
        followup.setEncounterDatetime(new Date());

        //Store Sputum Smear Result
        followup.setSputumSmear(testedOn, labNumber, result);

        //Save Encounter
        Encounter encounter = Context.getEncounterService().saveEncounter(followup.getEncounter());

        if (StringUtils.isNotEmpty(outcomeResults)){
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

            MdrtbPatientProgram program = Context.getService(MdrtbService.class).getMdrtbPatientProgram(programId);
            program.setDateCompleted(df.parse(testedOn, new ParsePosition(0)));
            program.setOutcome(Context.getProgramWorkflowService().getStateByUuid(outcomeResults));

            // save the actual update
            Context.getProgramWorkflowService().savePatientProgram(program.getPatientProgram());
        }

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

}
