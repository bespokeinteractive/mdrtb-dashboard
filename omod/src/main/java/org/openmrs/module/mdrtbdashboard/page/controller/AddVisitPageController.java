package org.openmrs.module.mdrtbdashboard.page.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.form.SimpleFollowUpForm;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtb.model.PatientProgramDetails;
import org.openmrs.module.mdrtb.model.PatientProgramRegimen;
import org.openmrs.module.mdrtb.model.PatientProgramVisits;
import org.openmrs.module.mdrtb.model.VisitTypes;
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
 * Created on 7/22/2017.
 */
public class AddVisitPageController {
    MdrtbDashboardService dashboardService = Context.getService(MdrtbDashboardService.class);
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);
    ConceptService conceptService = Context.getConceptService();

    public String get(@RequestParam(value = "programId", required = true) Integer programId,
                      PageModel model,
                      UiUtils ui) {
        MdrtbPatientProgram mpp = mdrtbService.getMdrtbPatientProgram(programId);
        PatientProgramDetails details = mdrtbService.getPatientProgramDetails(mpp);

        Collection<ConceptAnswer> regimenTypes = mdrtbService.getPossibleTbTreatmentTypes();
        Collection<ConceptAnswer> smearResults = mdrtbService.getPossibleSmearResults();
        Collection<ConceptAnswer> genXpertResults = mdrtbService.getPossibleGenXpertResults();
        Collection<ConceptAnswer> xrayTestResults = mdrtbService.getPossibleXRayTestResults();
        Collection<ConceptAnswer> hivTestResults = mdrtbService.getPossibleHivTestResults();

        List<PatientProgramVisits> ppvList = mdrtbService.getPatientProgramVisits(mpp.getPatientProgram());
        List<VisitTypes> visitTypes = mdrtbService.getVisitTypes(mpp.getPatientProgram().getProgram(), false, false, false);
        List<VisitTypes> visitFilter = new ArrayList<VisitTypes>();

        looper: for (VisitTypes type : visitTypes){
            for (PatientProgramVisits ppv: ppvList){
                if (ppv.getVisitType().equals(type)){
                    continue looper;
                }
            }
            visitFilter.add(type);
        }

        // Test if patient is Enrolled in Any program
        if (!(mpp != null && mpp.getActive())){
            return "redirect:" + ui.pageLink("mdrtbdashboard", "enroll")+"?patient="+mpp.getPatient().getId();
        }

        // Test if Intake Form was ever filled
        if (details.getFacility() == null){
            return "redirect:" + ui.pageLink("mdrtbdashboard", "intake")+"?patient="+mpp.getPatient().getId();
        }

        model.addAttribute("patient", mpp.getPatient());
        model.addAttribute("current", mpp);
        model.addAttribute("details", details);
        model.addAttribute("program", mpp.getPatientProgram());
        model.addAttribute("visitTypes", visitFilter);
        model.addAttribute("regimenTypes", regimenTypes);
        model.addAttribute("smearResults", smearResults);
        model.addAttribute("hivTestResults", hivTestResults);
        model.addAttribute("xrayTestResults", xrayTestResults);
        model.addAttribute("genXpertResults", genXpertResults);

        return null;
    }

    public String post(HttpServletRequest request,
                       UiUtils ui,
                       UiSessionContext session) throws IOException {
        VisitTypes vt = mdrtbService.getVisitType(Integer.parseInt(request.getParameter("visit.type")));

        MdrtbPatientProgram mpp = mdrtbService.getMdrtbPatientProgram(Integer.parseInt(request.getParameter("program.id")));
        PatientProgramDetails ppd = mdrtbService.getPatientProgramDetails(mpp);
        PatientProgramRegimen ppr = new PatientProgramRegimen();
        PatientProgramVisits ppv = mdrtbService.getPatientProgramVisit(mpp.getPatientProgram(), vt);

        if (ppv == null){
            ppv = new PatientProgramVisits();
            ppv.setPatientProgram(mpp.getPatientProgram());
            ppv.setVisitType(vt);
        }

        Map<String, Object> params=new HashMap<String, Object>();

        String height = request.getParameter("vitals.height");
        String weight = request.getParameter("vitals.weight");
        String muac = request.getParameter("vitals.muac");
        String bmi = request.getParameter("vitals.bmi");
        String regimenName = request.getParameter("regimen.name");
        String regimenType = request.getParameter("regimen.type");
        String hivResult = request.getParameter("exams.hiv.result");
        String labNumber = request.getParameter("exams.lab.number");

        if (StringUtils.equals(muac, "999")){
            muac = "";
        }

        if (StringUtils.equals(height, "999")){
            height = "";
        }

        if (StringUtils.equals(weight, "999")){
            weight = "";
        }

        Concept smear = conceptService.getConcept(30);
        Concept artstt;
        Concept cptstt;
        Concept hivConcept;

        if (request.getParameter("exams.sputum.result") != null){
            smear = conceptService.getConcept(Integer.parseInt(request.getParameter("exams.sputum.result")));
        }

        SimpleFollowUpForm followup = new SimpleFollowUpForm(mpp.getPatient());
        //Set up Encounter
        followup.setLocation(session.getSessionLocation());
        followup.setProvider(Context.getPersonService().getPerson(3));
        followup.setEncounterDatetime(new Date());
        followup.setWeight(weight);
        followup.setHeight(height);
        followup.setBMI(bmi);
        followup.setMUAC(muac);

        //Store Sputum Smear Result/Lab Number
        if (request.getParameter("exams.sputum.result") != null){
            followup.setSputumSmear(request.getParameter("exams.sputum.date"), labNumber, request.getParameter("exams.sputum.result"));
            ppv.setExamDate(getDateFromStrings(request.getParameter("exams.sputum.date")));
            ppv.setSputumResult(smear);
        }
        else {
            followup.setLabNumber(request.getParameter("exams.lab.number"));
            ppv.setExamDate(getDateFromStrings(request.getParameter("exams.genxpert.date")));
        }

        followup.setGenXpert(request.getParameter("exams.genxpert.date"), request.getParameter("exams.genxpert.result"));
        followup.setHivResults(request.getParameter("exams.hiv.date"), hivResult);
        followup.setXrayResults(request.getParameter("exams.xray.date"), request.getParameter("exams.xray.result"));

        if (!(request.getParameter("exams.art.started").equals("") || request.getParameter("exams.art.started").isEmpty())){
            followup.setPatientStartedOnArt(request.getParameter("exams.art.started"), request.getParameter("exams.art.date"));
            artstt = conceptService.getConcept(Integer.parseInt(request.getParameter("exams.art.started")));
            ppd.setArtStarted(artstt);
        }

        if (!(request.getParameter("exams.cpt.started").equals("") || request.getParameter("exams.cpt.started").isEmpty())){
            followup.setPatientStartedOnCpt(request.getParameter("exams.cpt.started"), request.getParameter("exams.cpt.date"));
            cptstt = conceptService.getConcept(Integer.parseInt(request.getParameter("exams.cpt.started")));
            ppd.setCptStarted(cptstt);
        }

        if (!hivResult.equals("30")){
            hivConcept = conceptService.getConcept(Integer.parseInt(hivResult));
            ppd.setCurrentStatus(hivConcept);
        }

        if (!ppd.getRegimen().getName().equals(regimenName) && StringUtils.isNotBlank(regimenName) && StringUtils.isNotBlank(regimenType)){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = df.parse(request.getParameter("regimen.started"), new ParsePosition(0));

            ppr.setProgramDetails(ppd);
            ppr.setName(request.getParameter("regimen.name"));
            ppr.setType(conceptService.getConcept(Integer.parseInt(request.getParameter("regimen.type"))));
            ppr.setStartedOn(date);
            ppr = mdrtbService.savePatientProgramRegimen(ppr);

            ppd.setRegimen(ppr);
        }

        //Save Encounter
        Encounter encounter = Context.getEncounterService().saveEncounter(followup.getEncounter());

        ppv.setEncounter(encounter);
        ppv.setLabNumber(labNumber);
        ppv.setGeneXpertResult(conceptService.getConcept(Integer.parseInt(request.getParameter("exams.genxpert.result"))));
        ppv.setHivResults(conceptService.getConcept(Integer.parseInt(request.getParameter("exams.hiv.result"))));
        ppv.setXrayResults(conceptService.getConcept(Integer.parseInt(request.getParameter("exams.xray.result"))));

        this.mdrtbService.savePatientProgramDetails(ppd);
        this.mdrtbService.savePatientProgramVisits(ppv);

        params.put("programId", mpp.getPatientProgram().getId());
        params.put("patient", mpp.getPatient().getId());
        return "redirect:" + ui.pageLink("mdrtbdashboard", "main", params);
    }

    public Date getDateFromStrings(String stringDate){
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        return df.parse(stringDate, new ParsePosition(0));
    }
}
