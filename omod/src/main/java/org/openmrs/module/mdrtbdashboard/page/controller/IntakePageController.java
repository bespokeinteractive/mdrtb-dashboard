package org.openmrs.module.mdrtbdashboard.page.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.form.SimpleIntakeForm;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationFacilities;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramRegimen;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Dennis Henry
 * Created on 12/31/2016.
 */
public class IntakePageController {
    MdrtbDashboardService dashboardService = Context.getService(MdrtbDashboardService.class);
    ConceptService conceptService = Context.getConceptService();
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public String get(
            @RequestParam(value = "patient") Patient patient,
            @RequestParam(value = "programId", required = false) Integer programId,
            PageModel model,
            UiUtils ui,
            UiSessionContext session) {

        MdrtbPatientProgram mpp = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        PatientProgramDetails details = dashboardService.getPatientProgramDetails(mpp);
        Location location = session.getSessionLocation();

        if (programId != null){
            mpp = mdrtbService.getMdrtbPatientProgram(programId);
            patient = mpp.getPatient();
        }

        if (mpp != null && mpp.getActive()){
            model.addAttribute("program", mpp.getPatientProgram().getProgram());
        }
        else{
            return "redirect:" + ui.pageLink("mdrtbdashboard", "enroll")+"?patient="+patient.getId();
        }

        if (details.getFacility() != null){
            if (programId == null){
                return "redirect:" + ui.pageLink("mdrtbdashboard", "main")+"?patient="+patient.getId();
            }
            else {
                return "redirect:" + ui.pageLink("mdrtbdashboard", "main")+"?patient="+patient.getId();
            }
        }

        List<LocationFacilities> facilities = dashboardService.getFacilities(location, "active");

        Collection<ConceptAnswer> anatomicalSites = mdrtbService.getPossibleAnatomicalSites();
        Collection<ConceptAnswer> siteConfirmation = mdrtbService.getPossibleAnatomicalSitesConfirmation();
        Collection<ConceptAnswer> directObservers = mdrtbService.getPossibleDirectObservers();
        Collection<ConceptAnswer> smearResults = mdrtbService.getPossibleSmearResults();
        Collection<ConceptAnswer> genXpertResults = mdrtbService.getPossibleGenXpertResults();
        Collection<ConceptAnswer> xrayTestResults = mdrtbService.getPossibleXRayTestResults();
        Collection<ConceptAnswer> hivTestResults = mdrtbService.getPossibleHivTestResults();
        Collection<ConceptAnswer> referringDepartments = mdrtbService.getPossibleReferringDepartments();
        Collection<ConceptAnswer> regimenTypes = mdrtbService.getPossibleTbTreatmentTypes();

        model.addAttribute("patient", patient);
        model.addAttribute("program", mpp.getPatientProgram());
        model.addAttribute("location", location);
        model.addAttribute("facilities", facilities);

        model.addAttribute("anatomicalSites", anatomicalSites);
        model.addAttribute("siteConfirmation", siteConfirmation);
        model.addAttribute("directObservers", directObservers);
        model.addAttribute("regimenTypes", regimenTypes);
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
        MdrtbPatientProgram mpp = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        PatientProgramDetails ppd = dashboardService.getPatientProgramDetails(mpp);
        PatientProgramRegimen ppr = new PatientProgramRegimen();

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
        //Definitions
        LocationFacilities facility = dashboardService.getFacilityById(Integer.parseInt(request.getParameter("treatment.facility")));
        Concept status = conceptService.getConcept(Integer.parseInt(request.getParameter("exams.hiv.result")));
        Concept smear = conceptService.getConcept(30);
        Concept artstt = conceptService.getConcept(126);
        Concept cptstt = conceptService.getConcept(126);

        if (request.getParameter("exams.sputum.result") != null){
            smear = conceptService.getConcept(Integer.parseInt(request.getParameter("exams.sputum.result")));
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
        intake.setFacilityRegisterNumber(request.getParameter("facility.number"));
        intake.setDirectObserver(request.getParameter("treatment.dots"));
        intake.setAnatomicalSite(ppd.getDiseaseSite());

        if (request.getParameter("exams.sputum.result") != null){
            intake.setSputumSmear(request.getParameter("exams.sputum.date"), request.getParameter("exams.lab.number"), request.getParameter("exams.sputum.result"));
        }
        else {
            intake.setLabNumber(request.getParameter("exams.lab.number"));
        }

        intake.setGenXpert(request.getParameter("exams.genxpert.date"), request.getParameter("exams.genxpert.result"));
        intake.setHivResults(request.getParameter("exams.hiv.date"), request.getParameter("exams.hiv.result"));
        intake.setXrayResults(request.getParameter("exams.xray.date"), request.getParameter("exams.xray.result"));

        if (!(request.getParameter("exams.art.started").equals("") || request.getParameter("exams.art.started").isEmpty())){
            intake.setPatientStartedOnArt(request.getParameter("exams.art.started"), request.getParameter("exams.art.date"));
            artstt = conceptService.getConcept(Integer.parseInt(request.getParameter("exams.art.started")));
        }

        if (!(request.getParameter("exams.cpt.started").equals("") || request.getParameter("exams.cpt.started").isEmpty())){
            intake.setPatientStartedOnCpt(request.getParameter("exams.cpt.started"), request.getParameter("exams.cpt.date"));
            cptstt = conceptService.getConcept(Integer.parseInt(request.getParameter("exams.cpt.started")));
        }

        //Obs Groups Fields
        Encounter encounter = Context.getEncounterService().saveEncounter(intake.getEncounter());

        ppd.setDaamin(daamin);
        ppd.setDaaminContacts(contacts);
        ppd.setFacility(facility);
        ppd.setSputumResults(smear);
        ppd.setInitialStatus(status);
        ppd.setCurrentStatus(status);
        ppd.setArtStarted(artstt);
        ppd.setCptStarted(cptstt);

        if (!(request.getParameter("regimen.type").equals("")) || request.getParameter("regimen.type").isEmpty()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = df.parse(request.getParameter("regimen.started"), new ParsePosition(0));
            intake.setTreatmentStartDate(request.getParameter("regimen.started"));

            ppr.setProgramDetails(ppd);
            ppr.setName(request.getParameter("regimen.name"));
            ppr.setType(conceptService.getConcept(Integer.parseInt(request.getParameter("regimen.type"))));
            ppr.setStartedOn(date);
            ppr = dashboardService.savePatientProgramRegimen(ppr);

            ppd.setRegimen(ppr);
        }

        this.dashboardService.savePatientProgramDetails(ppd);

        params.put("patient", patient.getId());
        return "redirect:" + ui.pageLink("mdrtbdashboard", "main", params);
    }
}