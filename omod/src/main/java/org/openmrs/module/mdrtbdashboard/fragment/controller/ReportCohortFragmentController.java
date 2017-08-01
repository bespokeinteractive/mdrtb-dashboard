package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationFacilities;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.reports.CohortReport;
import org.openmrs.module.mdrtbdashboard.util.DateRangeModel;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 7/31/2017.
 */
public class ReportCohortFragmentController {
    MdrtbDashboardService dashboard = Context.getService(MdrtbDashboardService.class);
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public void get(FragmentModel model,
                      FragmentConfiguration config,
                      UiUtils ui,
                      UiSessionContext session){
        CohortReport report = new CohortReport();

        config.require("qtr");
        config.require("year");
        config.require("facility");

        Integer quarter = Integer.parseInt(config.get("qtr").toString());
        Integer year = Integer.parseInt(config.get("year").toString());
        LocationFacilities facility = dashboard.getFacilityById(Integer.parseInt(config.get("facility").toString()));

        DateRangeModel dates = new DateRangeModel(quarter, year);
        List<PatientProgramDetails> patients = dashboard.getPatientsFromDetails(session.getSessionLocation(), dates.getStartDate(), dates.getEndDate(), facility);
        for (PatientProgramDetails patient: patients){
            //This is for TB Patients Only Who have completed Treated
            if (patient.getPatientProgram().getProgram().getProgramId() == 2 || patient.getOutcome() == null){
                continue;
            }

            Concept outcome = patient.getOutcome();

            //Patient Categories
            Boolean bcnr = false;
            Boolean cdnr = false;
            Boolean rtrr = false;
            Boolean hvap = false;

            if (patient.getConfirmationSite().equals(mdrtbService.getConcept(MdrtbConcepts.BACTERIOLOGICAL_CONFIRMED)) && (patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.NEW)) || patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.RELAPSE))) ){
                bcnr = true;
            }
            else if (patient.getConfirmationSite().equals(mdrtbService.getConcept(MdrtbConcepts.CLINICALLY_DIAGNOSED)) && (patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.NEW)) || patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.RELAPSE))) ){
                bcnr = true;
            }
            else {
                rtrr = true;
            }

            if (patient.getInitialStatus().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE)) || patient.getCurrentStatus().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE))){
                hvap = true;
            }

            //Outcome Categories
            Boolean cured = false;
            Boolean complete = false;
            Boolean failed = false;
            Boolean died = false;
            Boolean lost = false;
            Boolean notEval = false;

            if (outcome.equals(mdrtbService.getConcept(MdrtbConcepts.CURED))){
                cured = true;
            }
            else if (outcome.equals(mdrtbService.getConcept(MdrtbConcepts.TREATMENT_COMPLETE))){
                complete = true;
            }
            else if (outcome.equals(mdrtbService.getConcept(MdrtbConcepts.FAILED))){
                failed = true;
            }
            else if (outcome.equals(mdrtbService.getConcept(MdrtbConcepts.DIED))){
                died = true;
            }
            else if (outcome.equals(mdrtbService.getConcept(MdrtbConcepts.LOST_FOLLOWUP))){
                lost = true;
            }

            if (bcnr){
                if (cured){
                    report.setBcnrCured(report.getBcnrCured() + 1);
                }
                else if (complete){
                    report.setBcnrComplete(report.getBcnrComplete() + 1);
                }
                else if (failed){
                    report.setBcnrFailed(report.getBcnrFailed() + 1);
                }
                else if (died){
                    report.setBcnrDied(report.getBcnrDied() + 1);
                }
                else if (lost){
                    report.setBcnrLost(report.getBcnrLost() + 1);
                }
                else {
                    report.setBcnrOther(report.getBcnrOther() + 1);
                }
            }
            else if (cdnr){
                if (cured){
                    report.setCdnrCured(report.getCdnrCured() + 1);
                }
                else if (complete){
                    report.setCdnrComplete(report.getCdnrComplete() + 1);
                }
                else if (failed){
                    report.setCdnrFailed(report.getCdnrFailed() + 1);
                }
                else if (died){
                    report.setCdnrDied(report.getCdnrDied() + 1);
                }
                else if (lost){
                    report.setCdnrLost(report.getCdnrLost() + 1);
                }
                else {
                    report.setCdnrOther(report.getCdnrOther() + 1);
                }
            }
            else if (rtrr){
                if (cured){
                    report.setRtrrCured(report.getRtrrCured() + 1);
                }
                else if (complete){
                    report.setRtrrComplete(report.getRtrrComplete() + 1);
                }
                else if (failed){
                    report.setRtrrFailed(report.getRtrrFailed() + 1);
                }
                else if (died){
                    report.setRtrrDied(report.getRtrrDied() + 1);
                }
                else if (lost){
                    report.setRtrrLost(report.getRtrrLost() + 1);
                }
                else {
                    report.setRtrrOther(report.getRtrrOther() + 1);
                }
            }

            if (hvap){
                if (cured){
                    report.setHpatCured(report.getHpatCured() + 1);
                }
                else if (complete){
                    report.setHpatComplete(report.getHpatComplete() + 1);
                }
                else if (failed){
                    report.setHpatFailed(report.getHpatFailed() + 1);
                }
                else if (died){
                    report.setHpatDied(report.getHpatDied() + 1);
                }
                else if (lost){
                    report.setHpatLost(report.getHpatLost() + 1);
                }
                else {
                    report.setHpatOther(report.getHpatOther() + 1);
                }

                //HIV Status Table Parameters
                report.setHivStatus(report.getHivStatus() + 1);

                if (patient.getArtStarted().equals(mdrtbService.getConcept(MdrtbConcepts.YES))){
                    report.setStartedArt(report.getStartedArt() + 1);
                }

                if (patient.getCptStarted().equals(mdrtbService.getConcept(MdrtbConcepts.YES))){
                    report.setStartedCpt(report.getStartedCpt() + 1);
                }
            }
        }

        model.addAttribute("report", report);
    }
}
