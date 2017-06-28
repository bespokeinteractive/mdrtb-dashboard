package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationFacilities;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.reports.CaseFindingReport;
import org.openmrs.module.mdrtbdashboard.util.DateRangeModel;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.*;

/**
 * Created by Dennis Henry
 * Created on 5/9/2017.
 */
public class ReportCasefindingFragmentController {
    MdrtbDashboardService dashboard = Context.getService(MdrtbDashboardService.class);
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public String get(FragmentModel model,
                      FragmentConfiguration config,
                      UiUtils ui,
                      UiSessionContext session){
        CaseFindingReport report = new CaseFindingReport();

        config.require("qtr");
        config.require("year");
        config.require("facility");

        Integer quarter = Integer.parseInt(config.get("qtr").toString());
        Integer year = Integer.parseInt(config.get("year").toString());
        LocationFacilities facility = dashboard.getFacilityById(Integer.parseInt(config.get("facility").toString()));

        DateRangeModel dates = new DateRangeModel(quarter, year);
        List<PatientProgramDetails> patients = dashboard.getPatientsFromDetails(session.getSessionLocation(), dates.getStartDate(), dates.getEndDate(), facility);
        for (PatientProgramDetails patient: patients){
            if (patient.getPatientProgram().getProgram().getProgramId() == 2){
                //This is for TB Patients Only
                continue;
            }

            Boolean pbc = false;
            Boolean pcd = false;

            if (patient.getDiseaseSite().equals(mdrtbService.getConcept(MdrtbConcepts.PULMONARY_TB)) && patient.getSputumResults().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE))){
                pbc = true;
            }
            else if (patient.getDiseaseSite().equals(mdrtbService.getConcept(MdrtbConcepts.PULMONARY_TB)) && !patient.getSputumResults().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE))){
                pcd = true;
            }

            //Get Patient Category
            Boolean newPatient = false;
            Boolean relapse = false;
            Boolean treated = false;

            if (patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.NEW))){
                newPatient = true;
            }
            else if (patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.RELAPSE))){
                relapse = true;
            }
            else if (patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.FAILED)) || patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.LOST_FOLLOWUP)) && (patient.getPatientCategory().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.PREVIOUSLY_TREATED_FIRST_LINE_DRUGS_ONLY)) || patient.getPatientCategory().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.PREVIOUSLY_TREATED_SECOND_LINE_DRUGS)))){
                treated = true;
            }

            //Allocate the Details Back to the Reports
            if (pbc){
                if (newPatient){
                    report.setPbcNew(report.getPbcNew() + 1);
                }
                else if (relapse){
                    report.setPbcRelapse(report.getPbcRelapse() + 1);
                }
                else if (treated){
                    report.setPbcPrevTreated(report.getPbcPrevTreated() + 1);
                }
                else {
                    report.setPbcHistUnknown(report.getPbcHistUnknown() + 1);
                }
            }
            else if (pcd){
                if (newPatient){
                    report.setPcdNew(report.getPcdNew() + 1);
                }
                else if (relapse){
                    report.setPcdRelapse(report.getPcdRelapse() + 1);
                }
                else if (treated){
                    report.setPcdPrevTreated(report.getPcdPrevTreated() + 1);
                }
                else {
                    report.setPcdHistUnknown(report.getPcdHistUnknown() + 1);
                }
            }
            else {
                if (newPatient){
                    report.setEcdNew(report.getEcdNew() + 1);
                }
                else if (relapse){
                    report.setEcdRelapse(report.getEcdRelapse() + 1);
                }
                else if (treated){
                    report.setEcdPrevTreated(report.getEcdPrevTreated() + 1);
                }
                else {
                    report.setEcdHistUnknown(report.getEcdHistUnknown() + 1);
                }
            }

            Integer age = getEstimateAgeDuringEnrollment(patient.getPatientProgram().getPatient().getBirthdate(), patient.getPatientProgram().getDateEnrolled());
            if (patient.getPatientProgram().getPatient().getGender().equals("M")){
                if (age < 5){
                    report.setM0004(report.getM0004() + 1);
                }
                else if (age < 15){
                    report.setM0514(report.getM0514() + 1);
                }
                else if (age < 25){
                    report.setM1524(report.getM1524() + 1);
                }
                else if (age < 35){
                    report.setM2534(report.getM2534() + 1);
                }
                else if (age < 45){
                    report.setM3544(report.getM3544() + 1);
                }
                else if (age < 55){
                    report.setM4554(report.getM4554() + 1);
                }
                else if (age < 65){
                    report.setM5564(report.getM5564() + 1);
                }
                else {
                    report.setM65XX(report.getM65XX() + 1);
                }
            }
            else {
                if (age < 5){
                    report.setF0004(report.getF0004() + 1);
                }
                else if (age < 15){
                    report.setF0514(report.getF0514() + 1);
                }
                else if (age < 25){
                    report.setF1524(report.getF1524() + 1);
                }
                else if (age < 35){
                    report.setF2534(report.getF2534() + 1);
                }
                else if (age < 45){
                    report.setF3544(report.getF3544() + 1);
                }
                else if (age < 55){
                    report.setF4554(report.getF4554() + 1);
                }
                else if (age < 65){
                    report.setF5564(report.getF5564() + 1);
                }
                else {
                    report.setF65XX(report.getF65XX() + 1);
                }
            }

            //HIV Parameters
            if (patient.getInitialStatus().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE))){
                report.setInitialStatus(report.getInitialStatus() + 1);
            }

            if (patient.getCurrentStatus().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE))){
                report.setCurrentStatus(report.getCurrentStatus() + 1);
            }

            if (patient.getArtStarted().equals(mdrtbService.getConcept(MdrtbConcepts.YES))){
                report.setStartedArt(report.getStartedArt() + 1);
            }

            if (patient.getCptStarted().equals(mdrtbService.getConcept(MdrtbConcepts.YES))){
                report.setStartedCpt(report.getStartedCpt() + 1);
            }
        }

        model.addAttribute("report", report);
        return null;
    }

    public static Integer getEstimateAgeDuringEnrollment(Date birthDate, Date enrollmentDate) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal2.setTime(enrollmentDate);
        cal2.setTime(birthDate);

        int yearNew = cal1.get(Calendar.YEAR);
        int yearOld = cal2.get(Calendar.YEAR);
        //int yearDiff = yearNew - yearOld;
        //String ageYear = String.valueOf(yearDiff);
        return (yearNew - yearOld);
    }
}
