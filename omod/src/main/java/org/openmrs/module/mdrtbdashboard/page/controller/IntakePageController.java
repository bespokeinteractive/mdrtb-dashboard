package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.form.SimpleIntakeForm;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by Dennis Henry on 12/31/2016.
 */
public class IntakePageController {
    public String get(
            @RequestParam(value = "patient") Patient patient,
            PageModel model,
            UiUtils ui,
            UiSessionContext session) {
        MdrtbPatientProgram mostRecentProgram = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
        if (mostRecentProgram != null && mostRecentProgram.getActive()){
            model.addAttribute("program", mostRecentProgram.getPatientProgram().getProgram());
        }
        else{
            return "redirect:" + ui.pageLink("mdrtbdashboard", "enroll")+"?patient="+patient.getId();
        }

        List <Obs> list = Context.getObsService().getObservationsByPersonAndConcept(patient, Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB));
        for (Obs obs : list){
            if (obs.getObsDatetime().after(mostRecentProgram.getPatientProgram().getDateEnrolled())){
                return "redirect:" + ui.pageLink("mdrtbdashboard", "enroll")+"?patient="+patient.getId();
            }
        }

        Collection<ConceptAnswer> anatomicalSites = Context.getService(MdrtbService.class).getPossibleAnatomicalSites();
        Collection<ConceptAnswer> directObservers = Context.getService(MdrtbService.class).getPossibleDirectObservers();
        Collection<ConceptAnswer> smearResults = Context.getService(MdrtbService.class).getPossibleSmearResults();
        Collection<ConceptAnswer> genXpertResults = Context.getService(MdrtbService.class).getPossibleGenXpertResults();
        Collection<ConceptAnswer> xrayTestResults = Context.getService(MdrtbService.class).getPossibleXRayTestResults();
        Collection<ConceptAnswer> hivTestResults = Context.getService(MdrtbService.class).getPossibleHivTestResults();
        Collection<ConceptAnswer> referringDepartments = Context.getService(MdrtbService.class).getPossibleReferringDepartments();

        model.addAttribute("patient", patient);
        model.addAttribute("program", mostRecentProgram.getPatientProgram());
        model.addAttribute("location", session.getSessionLocation());
        model.addAttribute("anatomicalSites", anatomicalSites);
        model.addAttribute("directObservers", directObservers);
        model.addAttribute("smearResults", smearResults);
        model.addAttribute("hivTestResults", hivTestResults);
        model.addAttribute("xrayTestResults", xrayTestResults);
        model.addAttribute("genXpertResults", genXpertResults);
        model.addAttribute("referringDepartments", referringDepartments);

        return null;
    }

    public String post(HttpServletRequest request,
                       PageModel model,
                       UiUtils ui,
                       UiSessionContext session) throws IOException {
        Map<String, Object> params=new HashMap<String, Object>();
        Patient patient = Context.getPatientService().getPatient(Integer.parseInt(request.getParameter("patient.id")));

        SimpleIntakeForm intake = new SimpleIntakeForm(patient);
        // Fields for Encounter
        intake.setLocation(session.getSessionLocation());
        intake.setProvider(Context.getPersonService().getPerson(3));
        intake.setEncounterDatetime(new Date());

        // Obs Fields
        intake.setWeight(request.getParameter("vitals.weight"));
        intake.setHeight(request.getParameter("vitals.height"));
        intake.setBMI(request.getParameter("vitals.bmi"));
        intake.setMUAC(request.getParameter("vitals.muac"));

        intake.setHealthFacility(request.getParameter("treatment.facility"));
        intake.setTreatementSupporter(request.getParameter("treatment.supporter"));
        intake.setReferringDepartment(request.getParameter("treatment.referral"));

        intake.setDirectObserver(request.getParameter("treatment.dots"));
        intake.setTreatmentStartDate(request.getParameter("treatment.started"));
        intake.setAnatomicalSite(Context.getConceptService().getConcept(request.getParameter("treatment.site")));

        intake.setSputumSmear(request.getParameter("exams.sputum.date"), request.getParameter("exams.lab.number"), request.getParameter("exams.sputum.result"));
        intake.setGenXpert(request.getParameter("exams.genxpert.date"), request.getParameter("exams.genxpert.result"));
        intake.setHivResults(request.getParameter("exams.hiv.date"), request.getParameter("exams.hiv.result"));
        intake.setXrayResults(request.getParameter("exams.xray.date"), request.getParameter("exams.xray.result"));

        if (!(request.getParameter("exams.art.started").equals("") || request.getParameter("exams.art.started").isEmpty())){
            intake.setPatientStartedOnArt(request.getParameter("exams.art.started"), request.getParameter("exams.art.date"));
        }

        if (!(request.getParameter("exams.cpt.started").equals("") || request.getParameter("exams.cpt.started").isEmpty())){
            intake.setPatientStartedOnCpt(request.getParameter("exams.cpt.started"), request.getParameter("exams.cpt.date"));
        }

        //Obs Groups Fields
        Encounter encounter = Context.getEncounterService().saveEncounter(intake.getEncounter());

        params.put("patient", patient.getId());
        return "redirect:" + ui.pageLink("mdrtbdashboard", "main", params);

    }
}
