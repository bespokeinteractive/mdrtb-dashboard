package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
        Collections.reverse(visitSummaries);

        model.addAttribute("smearResults", smearResults);
        model.addAttribute("visitSummaries", visitSummaries);
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

}
