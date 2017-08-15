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

    public void get(FragmentModel model,
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

            //Get Patient Categoty
            Boolean pbc = false;
            Boolean pcd = false;
            Boolean exp = false;

            if (patient.getDiseaseSite().equals(mdrtbService.getConcept(MdrtbConcepts.PULMONARY_TB)) && patient.getConfirmationSite().equals(mdrtbService.getConcept(MdrtbConcepts.BACTERIOLOGICAL_CONFIRMED))){
                pbc = true;
            }
            else if (patient.getDiseaseSite().equals(mdrtbService.getConcept(MdrtbConcepts.PULMONARY_TB)) && patient.getConfirmationSite().equals(mdrtbService.getConcept(MdrtbConcepts.CLINICALLY_DIAGNOSED))){
                pcd = true;
            }
            else {
                exp = true;
            }

            //Get Patient Type
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

            //Get HIV Status
            Boolean hivTested = false;
            Boolean hivPositive = false;

            if (!(patient.getInitialStatus().equals(mdrtbService.getConcept(MdrtbConcepts.NOT_DONE)) && patient.getCurrentStatus().equals(mdrtbService.getConcept(MdrtbConcepts.NOT_DONE)))){
                hivTested = true;
            }
            if (patient.getInitialStatus().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE)) || patient.getCurrentStatus().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE))){
                hivPositive = true;
            }

            //Block 1. Summary of all cases
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

            //Block 2: Age and Sex distribution
            Integer age = getEstimateAgeDuringEnrollment(patient.getPatientProgram().getPatient().getBirthdate(), patient.getPatientProgram().getDateEnrolled());
            if (pbc && newPatient){
                if (patient.getPatientProgram().getPatient().getGender().equals("M")){
                    //Totals/Summary Block
                    report.setBcnmTotal(report.getBcnmTotal() + 1);
                    if (hivTested)
                        report.setBctmTotal(report.getBctmTotal() + 1);
                    if (hivPositive)
                        report.setBcpmTotal(report.getBcpmTotal() + 1);

                    //Age Distribution Block
                    if (age < 5){
                        report.setBcnm0004(report.getBcnm0004() + 1);
                        if (hivTested)
                            report.setBctm0004(report.getBctm0004() + 1);
                        if (hivPositive)
                            report.setBcpm0004(report.getBcpm0004() + 1);
                    }
                    else if (age < 15){
                        report.setBcnm0514(report.getBcnm0514() + 1);
                        if (hivTested)
                            report.setBctm0514(report.getBctm0514() + 1);
                        if (hivPositive)
                            report.setBcpm0514(report.getBcpm0514() + 1);
                    }
                    else if (age < 25){
                        report.setBcnm1524(report.getBcnm1524() + 1);
                        if (hivTested)
                            report.setBctm1524(report.getBctm1524() + 1);
                        if (hivPositive)
                            report.setBcpm1524(report.getBcpm1524() + 1);
                    }
                    else if (age < 35){
                        report.setBcnm2534(report.getBcnm2534() + 1);
                        if (hivTested)
                            report.setBctm2534(report.getBctm2534() + 1);
                        if (hivPositive)
                            report.setBcpm2534(report.getBcpm2534() + 1);
                    }
                    else if (age < 45){
                        report.setBcnm3544(report.getBcnm3544() + 1);
                        if (hivTested)
                            report.setBctm3544(report.getBctm3544() + 1);
                        if (hivPositive)
                            report.setBcpm3544(report.getBcpm3544() + 1);
                    }
                    else if (age < 55){
                        report.setBcnm4554(report.getBcnm4554() + 1);
                        if (hivTested)
                            report.setBctm4554(report.getBctm4554() + 1);
                        if (hivPositive)
                            report.setBcpm4554(report.getBcpm4554() + 1);
                    }
                    else if (age < 65){
                        report.setBcnm5564(report.getBcnm5564() + 1);
                        if (hivTested)
                            report.setBctm5564(report.getBctm5564() + 1);
                        if (hivPositive)
                            report.setBcpm5564(report.getBcpm5564() + 1);
                    }
                    else {
                        report.setBcnm65XX(report.getBcnm65XX() + 1);
                        if (hivTested)
                            report.setBctm65XX(report.getBctm65XX() + 1);
                        if (hivPositive)
                            report.setBcpm65XX(report.getBcpm65XX() + 1);
                    }
                }
                else {
                    //Totals/Summary Block
                    report.setBcnfTotal(report.getBcnfTotal() + 1);
                    if (hivTested)
                        report.setBctfTotal(report.getBctfTotal() + 1);
                    if (hivPositive)
                        report.setBcpfTotal(report.getBcpfTotal() + 1);

                    //Age Distribution Block
                    if (age < 5){
                        report.setBcnf0004(report.getBcnf0004() + 1);
                        if (hivTested)
                            report.setBctf0004(report.getBctf0004() + 1);
                        if (hivPositive)
                            report.setBcpf0004(report.getBcpf0004() + 1);
                    }
                    else if (age < 15){
                        report.setBcnf0514(report.getBcnf0514() + 1);
                        if (hivTested)
                            report.setBctf0514(report.getBctf0514() + 1);
                        if (hivPositive)
                            report.setBcpf0514(report.getBcpf0514() + 1);
                    }
                    else if (age < 25){
                        report.setBcnf1524(report.getBcnf1524() + 1);
                        if (hivTested)
                            report.setBctf1524(report.getBctf1524() + 1);
                        if (hivPositive)
                            report.setBcpf1524(report.getBcpf1524() + 1);
                    }
                    else if (age < 35){
                        report.setBcnf2534(report.getBcnf2534() + 1);
                        if (hivTested)
                            report.setBctf2534(report.getBctf2534() + 1);
                        if (hivPositive)
                            report.setBcpf2534(report.getBcpf2534() + 1);
                    }
                    else if (age < 45){
                        report.setBcnf3544(report.getBcnf3544() + 1);
                        if (hivTested)
                            report.setBctf3544(report.getBctf3544() + 1);
                        if (hivPositive)
                            report.setBcpf3544(report.getBcpf3544() + 1);
                    }
                    else if (age < 55){
                        report.setBcnf4554(report.getBcnf4554() + 1);
                        if (hivTested)
                            report.setBctf4554(report.getBctf4554() + 1);
                        if (hivPositive)
                            report.setBcpf4554(report.getBcpf4554() + 1);
                    }
                    else if (age < 65){
                        report.setBcnf5564(report.getBcnf5564() + 1);
                        if (hivTested)
                            report.setBctf5564(report.getBctf5564() + 1);
                        if (hivPositive)
                            report.setBcpf5564(report.getBcpf5564() + 1);
                    }
                    else {
                        report.setBcnf65XX(report.getBcnf65XX() + 1);
                        if (hivTested)
                            report.setBctf65XX(report.getBctf65XX() + 1);
                        if (hivPositive)
                            report.setBcpf65XX(report.getBcpf65XX() + 1);
                    }
                }
            }
            else if (pcd && newPatient){
                if (patient.getPatientProgram().getPatient().getGender().equals("M")){
                    //Totals/Summary Block
                    report.setCdnmTotal(report.getCdnmTotal() + 1);
                    if (hivTested)
                        report.setCdtmTotal(report.getCdtmTotal() + 1);
                    if (hivPositive)
                        report.setCdpmTotal(report.getCdpmTotal() + 1);

                    //Age Distribution Block
                    if (age < 5){
                        report.setCdnm0004(report.getCdnm0004() + 1);
                        if (hivTested)
                            report.setCdtm0004(report.getCdtm0004() + 1);
                        if (hivPositive)
                            report.setCdpm0004(report.getCdpm0004() + 1);
                    }
                    else if (age < 15){
                        report.setCdnm0514(report.getCdnm0514() + 1);
                        if (hivTested)
                            report.setCdtm0514(report.getCdtm0514() + 1);
                        if (hivPositive)
                            report.setCdpm0514(report.getCdpm0514() + 1);
                    }
                    else if (age < 25){
                        report.setCdnm1524(report.getCdnm1524() + 1);
                        if (hivTested)
                            report.setCdtm1524(report.getCdtm1524() + 1);
                        if (hivPositive)
                            report.setCdpm1524(report.getCdpm1524() + 1);
                    }
                    else if (age < 35){
                        report.setCdnm2534(report.getCdnm2534() + 1);
                        if (hivTested)
                            report.setCdtm2534(report.getCdtm2534() + 1);
                        if (hivPositive)
                            report.setCdpm2534(report.getCdpm2534() + 1);
                    }
                    else if (age < 45){
                        report.setCdnm3544(report.getCdnm3544() + 1);
                        if (hivTested)
                            report.setCdtm3544(report.getCdtm3544() + 1);
                        if (hivPositive)
                            report.setCdpm3544(report.getCdpm3544() + 1);
                    }
                    else if (age < 55){
                        report.setCdnm4554(report.getCdnm4554() + 1);
                        if (hivTested)
                            report.setCdtm4554(report.getCdtm4554() + 1);
                        if (hivPositive)
                            report.setCdpm4554(report.getCdpm4554() + 1);
                    }
                    else if (age < 65){
                        report.setCdnm5564(report.getCdnm5564() + 1);
                        if (hivTested)
                            report.setCdtm5564(report.getCdtm5564() + 1);
                        if (hivPositive)
                            report.setCdpm5564(report.getCdpm5564() + 1);
                    }
                    else {
                        report.setCdnm65XX(report.getCdnm65XX() + 1);
                        if (hivTested)
                            report.setCdtm65XX(report.getCdtm65XX() + 1);
                        if (hivPositive)
                            report.setCdpm65XX(report.getCdpm65XX() + 1);
                    }
                }
                else {
                    //Totals/Summary Block
                    report.setCdnfTotal(report.getCdnfTotal() + 1);
                    if (hivTested)
                        report.setCdtfTotal(report.getCdtfTotal() + 1);
                    if (hivPositive)
                        report.setCdpfTotal(report.getCdpfTotal() + 1);

                    //Age Distribution Block
                    if (age < 5){
                        report.setCdnf0004(report.getCdnf0004() + 1);
                        if (hivTested)
                            report.setCdtf0004(report.getCdtf0004() + 1);
                        if (hivPositive)
                            report.setCdpf0004(report.getCdpf0004() + 1);
                    }
                    else if (age < 15){
                        report.setCdnf0514(report.getCdnf0514() + 1);
                        if (hivTested)
                            report.setCdtf0514(report.getCdtf0514() + 1);
                        if (hivPositive)
                            report.setCdpf0514(report.getCdpf0514() + 1);
                    }
                    else if (age < 25){
                        report.setCdnf1524(report.getCdnf1524() + 1);
                        if (hivTested)
                            report.setCdtf1524(report.getCdtf1524() + 1);
                        if (hivPositive)
                            report.setCdpf1524(report.getCdpf1524() + 1);
                    }
                    else if (age < 35){
                        report.setCdnf2534(report.getCdnf2534() + 1);
                        if (hivTested)
                            report.setCdtf2534(report.getCdtf2534() + 1);
                        if (hivPositive)
                            report.setCdpf2534(report.getCdpf2534() + 1);
                    }
                    else if (age < 45){
                        report.setCdnf3544(report.getCdnf3544() + 1);
                        if (hivTested)
                            report.setCdtf3544(report.getCdtf3544() + 1);
                        if (hivPositive)
                            report.setCdpf3544(report.getCdpf3544() + 1);
                    }
                    else if (age < 55){
                        report.setCdnf4554(report.getCdnf4554() + 1);
                        if (hivTested)
                            report.setCdtf4554(report.getCdtf4554() + 1);
                        if (hivPositive)
                            report.setCdpf4554(report.getCdpf4554() + 1);
                    }
                    else if (age < 65){
                        report.setCdnf5564(report.getCdnf5564() + 1);
                        if (hivTested)
                            report.setCdtf5564(report.getCdtf5564() + 1);
                        if (hivPositive)
                            report.setCdpf5564(report.getCdpf5564() + 1);
                    }
                    else {
                        report.setCdnf65XX(report.getCdnf65XX() + 1);
                        if (hivTested)
                            report.setCdtf65XX(report.getCdtf65XX() + 1);
                        if (hivPositive)
                            report.setCdpf65XX(report.getCdpf65XX() + 1);
                    }
                }
            }
            //Previous Treated
            else if ((pbc || pcd) && !newPatient){
                if (patient.getPatientProgram().getPatient().getGender().equals("M")){
                    //Totals/Summary Block
                    report.setPtamTotal(report.getPtamTotal() + 1);
                    if (hivTested)
                        report.setPttmTotal(report.getPttmTotal() + 1);
                    if (hivPositive)
                        report.setPtpmTotal(report.getPtpmTotal() + 1);

                    //Age Distribution Block
                    if (age < 5){
                        report.setPtam0004(report.getPtam0004() + 1);
                        if (hivTested)
                            report.setPttm0004(report.getPttm0004() + 1);
                        if (hivPositive)
                            report.setPtpm0004(report.getPtpm0004() + 1);
                    }
                    else if (age < 15){
                        report.setPtam0514(report.getPtam0514() + 1);
                        if (hivTested)
                            report.setPttm0514(report.getPttm0514() + 1);
                        if (hivPositive)
                            report.setPtpm0514(report.getPtpm0514() + 1);
                    }
                    else if (age < 25){
                        report.setPtam1524(report.getPtam1524() + 1);
                        if (hivTested)
                            report.setPttm1524(report.getPttm1524() + 1);
                        if (hivPositive)
                            report.setPtpm1524(report.getPtpm1524() + 1);
                    }
                    else if (age < 35){
                        report.setPtam2534(report.getPtam2534() + 1);
                        if (hivTested)
                            report.setPttm2534(report.getPttm2534() + 1);
                        if (hivPositive)
                            report.setPtpm2534(report.getPtpm2534() + 1);
                    }
                    else if (age < 45){
                        report.setPtam3544(report.getPtam3544() + 1);
                        if (hivTested)
                            report.setPttm3544(report.getPttm3544() + 1);
                        if (hivPositive)
                            report.setPtpm3544(report.getPtpm3544() + 1);
                    }
                    else if (age < 55){
                        report.setPtam4554(report.getPtam4554() + 1);
                        if (hivTested)
                            report.setPttm4554(report.getPttm4554() + 1);
                        if (hivPositive)
                            report.setPtpm4554(report.getPtpm4554() + 1);
                    }
                    else if (age < 65){
                        report.setPtam5564(report.getPtam5564() + 1);
                        if (hivTested)
                            report.setPttm5564(report.getPttm5564() + 1);
                        if (hivPositive)
                            report.setPtpm5564(report.getPtpm5564() + 1);
                    }
                    else {
                        report.setPtam65XX(report.getPtam65XX() + 1);
                        if (hivTested)
                            report.setPttm65XX(report.getPttm65XX() + 1);
                        if (hivPositive)
                            report.setPtpm65XX(report.getPtpm65XX() + 1);
                    }
                }
                else {
                    //Totals/Summary Block
                    report.setPtafTotal(report.getPtafTotal() + 1);
                    if (hivTested)
                        report.setPttfTotal(report.getPttfTotal() + 1);
                    if (hivPositive)
                        report.setPtpfTotal(report.getPtpfTotal() + 1);

                    //Age Distribution Block
                    if (age < 5){
                        report.setPtaf0004(report.getPtaf0004() + 1);
                        if (hivTested)
                            report.setPttf0004(report.getPttf0004() + 1);
                        if (hivPositive)
                            report.setPtpf0004(report.getPtpf0004() + 1);
                    }
                    else if (age < 15){
                        report.setPtaf0514(report.getPtaf0514() + 1);
                        if (hivTested)
                            report.setPttf0514(report.getPttf0514() + 1);
                        if (hivPositive)
                            report.setPtpf0514(report.getPtpf0514() + 1);
                    }
                    else if (age < 25){
                        report.setPtaf1524(report.getPtaf1524() + 1);
                        if (hivTested)
                            report.setPttf1524(report.getPttf1524() + 1);
                        if (hivPositive)
                            report.setPtpf1524(report.getPtpf1524() + 1);
                    }
                    else if (age < 35){
                        report.setPtaf2534(report.getPtaf2534() + 1);
                        if (hivTested)
                            report.setPttf2534(report.getPttf2534() + 1);
                        if (hivPositive)
                            report.setPtpf2534(report.getPtpf2534() + 1);
                    }
                    else if (age < 45){
                        report.setPtaf3544(report.getPtaf3544() + 1);
                        if (hivTested)
                            report.setPttf3544(report.getPttf3544() + 1);
                        if (hivPositive)
                            report.setPtpf3544(report.getPtpf3544() + 1);
                    }
                    else if (age < 55){
                        report.setPtaf4554(report.getPtaf4554() + 1);
                        if (hivTested)
                            report.setPttf4554(report.getPttf4554() + 1);
                        if (hivPositive)
                            report.setPtpf4554(report.getPtpf4554() + 1);
                    }
                    else if (age < 65){
                        report.setPtaf5564(report.getPtaf5564() + 1);
                        if (hivTested)
                            report.setPttf5564(report.getPttf5564() + 1);
                        if (hivPositive)
                            report.setPtpf5564(report.getPtpf5564() + 1);
                    }
                    else {
                        report.setPtaf65XX(report.getPtaf65XX() + 1);
                        if (hivTested)
                            report.setPttf65XX(report.getPttf65XX() + 1);
                        if (hivPositive)
                            report.setPtpf65XX(report.getPtpf65XX() + 1);
                    }
                }
            }
            else {
                if (patient.getPatientProgram().getPatient().getGender().equals("M")){
                    //Totals/Summary Block
                    report.setEpamTotal(report.getEpamTotal() + 1);
                    if (hivTested)
                        report.setEptmTotal(report.getEptmTotal() + 1);
                    if (hivPositive)
                        report.setEppmTotal(report.getEppmTotal() + 1);

                    //Age Distribution Block
                    if (age < 5){
                        report.setEpam0004(report.getEpam0004() + 1);
                        if (hivTested)
                            report.setEptm0004(report.getEptm0004() + 1);
                        if (hivPositive)
                            report.setEppm0004(report.getEppm0004() + 1);
                    }
                    else if (age < 15){
                        report.setEpam0514(report.getEpam0514() + 1);
                        if (hivTested)
                            report.setEptm0514(report.getEptm0514() + 1);
                        if (hivPositive)
                            report.setEppm0514(report.getEppm0514() + 1);
                    }
                    else if (age < 25){
                        report.setEpam1524(report.getEpam1524() + 1);
                        if (hivTested)
                            report.setEptm1524(report.getEptm1524() + 1);
                        if (hivPositive)
                            report.setEppm1524(report.getEppm1524() + 1);
                    }
                    else if (age < 35){
                        report.setEpam2534(report.getEpam2534() + 1);
                        if (hivTested)
                            report.setEptm2534(report.getEptm2534() + 1);
                        if (hivPositive)
                            report.setEppm2534(report.getEppm2534() + 1);
                    }
                    else if (age < 45){
                        report.setEpam3544(report.getEpam3544() + 1);
                        if (hivTested)
                            report.setEptm3544(report.getEptm3544() + 1);
                        if (hivPositive)
                            report.setEppm3544(report.getEppm3544() + 1);
                    }
                    else if (age < 55){
                        report.setEpam4554(report.getEpam4554() + 1);
                        if (hivTested)
                            report.setEptm4554(report.getEptm4554() + 1);
                        if (hivPositive)
                            report.setEppm4554(report.getEppm4554() + 1);
                    }
                    else if (age < 65){
                        report.setEpam5564(report.getEpam5564() + 1);
                        if (hivTested)
                            report.setEptm5564(report.getEptm5564() + 1);
                        if (hivPositive)
                            report.setEppm5564(report.getEppm5564() + 1);
                    }
                    else {
                        report.setEpam65XX(report.getEpam65XX() + 1);
                        if (hivTested)
                            report.setEptm65XX(report.getEptm65XX() + 1);
                        if (hivPositive)
                            report.setEppm65XX(report.getEppm65XX() + 1);
                    }
                }
                else {
                    //Totals/Summary Block
                    report.setEpafTotal(report.getEpafTotal() + 1);
                    if (hivTested)
                        report.setEptfTotal(report.getEptfTotal() + 1);
                    if (hivPositive)
                        report.setEppfTotal(report.getEppfTotal() + 1);

                    //Age Distribution Block
                    if (age < 5){
                        report.setEpaf0004(report.getEpaf0004() + 1);
                        if (hivTested)
                            report.setEptf0004(report.getEptf0004() + 1);
                        if (hivPositive)
                            report.setEppf0004(report.getEppf0004() + 1);
                    }
                    else if (age < 15){
                        report.setEpaf0514(report.getEpaf0514() + 1);
                        if (hivTested)
                            report.setEptf0514(report.getEptf0514() + 1);
                        if (hivPositive)
                            report.setEppf0514(report.getEppf0514() + 1);
                    }
                    else if (age < 25){
                        report.setEpaf1524(report.getEpaf1524() + 1);
                        if (hivTested)
                            report.setEptf1524(report.getEptf1524() + 1);
                        if (hivPositive)
                            report.setEppf1524(report.getEppf1524() + 1);
                    }
                    else if (age < 35){
                        report.setEpaf2534(report.getEpaf2534() + 1);
                        if (hivTested)
                            report.setEptf2534(report.getEptf2534() + 1);
                        if (hivPositive)
                            report.setEppf2534(report.getEppf2534() + 1);
                    }
                    else if (age < 45){
                        report.setEpaf3544(report.getEpaf3544() + 1);
                        if (hivTested)
                            report.setEptf3544(report.getEptf3544() + 1);
                        if (hivPositive)
                            report.setEppf3544(report.getEppf3544() + 1);
                    }
                    else if (age < 55){
                        report.setEpaf4554(report.getEpaf4554() + 1);
                        if (hivTested)
                            report.setEptf4554(report.getEptf4554() + 1);
                        if (hivPositive)
                            report.setEppf4554(report.getEppf4554() + 1);
                    }
                    else if (age < 65){
                        report.setEpaf5564(report.getEpaf5564() + 1);
                        if (hivTested)
                            report.setEptf5564(report.getEptf5564() + 1);
                        if (hivPositive)
                            report.setEppf5564(report.getEppf5564() + 1);
                    }
                    else {
                        report.setEpaf65XX(report.getEpaf65XX() + 1);
                        if (hivTested)
                            report.setEptf65XX(report.getEptf65XX() + 1);
                        if (hivPositive)
                            report.setEppf65XX(report.getEppf65XX() + 1);
                    }
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
