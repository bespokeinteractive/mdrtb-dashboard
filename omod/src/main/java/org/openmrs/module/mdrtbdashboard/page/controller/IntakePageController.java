package org.openmrs.module.mdrtbdashboard.page.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.form.SimpleIntakeForm;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationFacilities;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by Dennis Henry
 * Created on 12/31/2016.
 */
public class IntakePageController {
    MdrtbDashboardService dashboardService = Context.getService(MdrtbDashboardService.class);
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public String get(
            @RequestParam(value = "patient") Patient patient,
            PageModel model,
            UiUtils ui,
            UiSessionContext session) {

        MdrtbPatientProgram mostRecentProgram = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        Location location = session.getSessionLocation();

        if (mostRecentProgram != null && mostRecentProgram.getActive()){
            model.addAttribute("program", mostRecentProgram.getPatientProgram().getProgram());
        }
        else{
            return "redirect:" + ui.pageLink("mdrtbdashboard", "enroll")+"?patient="+patient.getId();
        }

        List <Obs> list = Context.getObsService().getObservationsByPersonAndConcept(patient, mdrtbService.getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB));
        for (Obs obs : list){
            if (obs.getObsDatetime().after(mostRecentProgram.getPatientProgram().getDateEnrolled())){
                return "redirect:" + ui.pageLink("mdrtbdashboard", "enroll")+"?patient="+patient.getId();
            }
        }

        List<LocationFacilities> facilities = dashboardService.getFacilities(location, "active");

        Collection<ConceptAnswer> anatomicalSites = mdrtbService.getPossibleAnatomicalSites();
        Collection<ConceptAnswer> directObservers = mdrtbService.getPossibleDirectObservers();
        Collection<ConceptAnswer> smearResults = mdrtbService.getPossibleSmearResults();
        Collection<ConceptAnswer> genXpertResults = mdrtbService.getPossibleGenXpertResults();
        Collection<ConceptAnswer> xrayTestResults = mdrtbService.getPossibleXRayTestResults();
        Collection<ConceptAnswer> hivTestResults = mdrtbService.getPossibleHivTestResults();
        Collection<ConceptAnswer> referringDepartments = mdrtbService.getPossibleReferringDepartments();


        model.addAttribute("patient", patient);
        model.addAttribute("program", mostRecentProgram.getPatientProgram());
        model.addAttribute("location", location);
        model.addAttribute("facilities", facilities);

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
                       UiUtils ui,
                       UiSessionContext session) throws IOException {
        Map<String, Object> params=new HashMap<String, Object>();
        Patient patient = Context.getPatientService().getPatient(Integer.parseInt(request.getParameter("patient.id")));

        String height = request.getParameter("vitals.height");
        String weight = request.getParameter("vitals.weight");
        String muac = request.getParameter("vitals.muac");
        String bmi = request.getParameter("vitals.bmi");
        String daamin = request.getParameter("treatment.supporter");
        String contacts = request.getParameter("treatment.supporter.contacts");

        if (StringUtils.equals(muac, "999")){
            muac = "";
        }

        if (StringUtils.equals(height, "999")){
            height = "";
        }

        if (StringUtils.equals(weight, "999")){
            weight = "";
        }

        // Fields for Encounter
        SimpleIntakeForm intake = new SimpleIntakeForm(patient);
        intake.setLocation(session.getSessionLocation());
        intake.setProvider(Context.getPersonService().getPerson(3));
        intake.setEncounterDatetime(new Date());

        // Obs Fields
        intake.setWeight(weight);
        intake.setHeight(height);
        intake.setBMI(bmi);
        intake.setMUAC(muac);

        intake.setHealthFacility(request.getParameter("treatment.facility"));
        intake.setTreatementSupporter(daamin);
        intake.setReferringDepartment(request.getParameter("treatment.referral"));
        intake.setSecondLineRegistrationDate(request.getParameter("register.date"));
        intake.setSecondLineRegistrationNumber(request.getParameter("register.number"));
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
        MdrtbPatientProgram mpp = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        PatientProgramDetails ppd = dashboardService.getPatientProgramDetails(mpp);
        ppd.setDaamin(daamin);
        ppd.setDaaminContacts(contacts);
        ppd = dashboardService.savePatientProgramDetails(ppd);

        params.put("patient", patient.getId());
        return "redirect:" + ui.pageLink("mdrtbdashboard", "main", params);
    }
}
