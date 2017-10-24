package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationFacilities;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramVisits;
import org.openmrs.module.mdrtbdashboard.model.VisitTypes;
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

        for (PatientProgramDetails patient: patients) {
            //This is for TB Patients Only, Ignoring Voided Ones
            if (patient.getPatientProgram().getProgram().getProgramId() == 2 || patient.getPatientProgram().getVoided()) {
                continue;
            }

            Concept outcome = patient.getOutcome();

            //Disease Site (Category)
            Boolean ptb = false; //Pulmonary TB
            Boolean eptb = false; //Extra-Pulmonary TB

            if (patient.getDiseaseSite().equals(mdrtbService.getConcept(MdrtbConcepts.PULMONARY_TB))){
                ptb = true;
            }
            else if (patient.getDiseaseSite().equals(mdrtbService.getConcept(MdrtbConcepts.EXTRA_PULMONARY_TB))){
                eptb = true;
            }

            //Confirmation
            Boolean bc = false; //Bacteriologically Confirmed
            Boolean cd = false; //Clinically Diagnosed

            if (patient.getConfirmationSite().equals(mdrtbService.getConcept(MdrtbConcepts.BACTERIOLOGICAL_CONFIRMED))){
                bc = true;
            }
            else if (patient.getConfirmationSite().equals(mdrtbService.getConcept(MdrtbConcepts.BACTERIOLOGICAL_CONFIRMED))){
                cd = true;
            }

            //Categories
            Boolean np = false; //New Patient
            Boolean pt = false; //Previous Treated
            Boolean tf = false; //Treatement Failure
            Boolean lf = false; //Lost to Follow-up

            if (patient.getPatientType() == null){
                continue;
            }
            else if (patient.getPatientType().getConcept().equals(mdrtbService.getConcept(MdrtbConcepts.NEW))){
                np = true;
            }
            else {
                pt = true;

                if (patient.getPatientType().equals(mdrtbService.getConcept(MdrtbConcepts.FAILED))){
                    tf = true;
                }
                else if (patient.getPatientType().equals(mdrtbService.getConcept(MdrtbConcepts.LOST_FOLLOWUP))){
                    lf = true;
                }
            }

            //Outcome Categories
            Boolean died = false;
            Boolean lost = false;
            Boolean complete = false;
            Boolean notEval = false;

            if (outcome != null && outcome.equals(mdrtbService.getConcept(MdrtbConcepts.DIED))) {
                died = true;
            } else if (outcome != null && outcome.equals(mdrtbService.getConcept(MdrtbConcepts.LOST_FOLLOWUP))) {
                lost = true;
            } else if (outcome != null && outcome.equals(mdrtbService.getConcept(MdrtbConcepts.TREATMENT_COMPLETE))) {
                complete = true;
            } else if (outcome != null && outcome.equals(mdrtbService.getConcept(MdrtbConcepts.PATIENT_TRANSFERRED_OUT))) {
                notEval = true;
            }

            //HIV Status
            Boolean hiv_pos = false;
            Boolean hiv_neg = false;
            Boolean hiv_not = false;
            Boolean hiv_art = false;
            Boolean hiv_cpt = false;

            if (patient.getInitialStatus().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE)) || patient.getCurrentStatus().equals(mdrtbService.getConcept(MdrtbConcepts.POSITIVE))){
                hiv_pos = true;

                if (patient.getArtStarted().equals(mdrtbService.getConcept(MdrtbConcepts.YES))){
                    hiv_art = true;
                }

                if (patient.getCptStarted().equals(mdrtbService.getConcept(MdrtbConcepts.YES))){
                    hiv_cpt = true;
                }
            }
            else if (patient.getInitialStatus().equals(mdrtbService.getConcept(MdrtbConcepts.NEGATIVE)) || patient.getCurrentStatus().equals(mdrtbService.getConcept(MdrtbConcepts.NEGATIVE))){
                hiv_neg = true;
            }
            else if (patient.getInitialStatus().equals(mdrtbService.getConcept(MdrtbConcepts.NOT_DONE)) || patient.getCurrentStatus().equals(mdrtbService.getConcept(MdrtbConcepts.NOT_DONE))){
                hiv_not = true;
            }


            // Visit Status
            VisitTypes vt2 = dashboard.getVisitType(patient.getPatientProgram().getProgram(), VisitTypes.TB_VISIT_02);
            VisitTypes vt5 = dashboard.getVisitType(patient.getPatientProgram().getProgram(), VisitTypes.TB_VISIT_05);
            VisitTypes vt8 = dashboard.getVisitType(patient.getPatientProgram().getProgram(), VisitTypes.TB_VISIT_08);

            PatientProgramVisits ppv2 = dashboard.getPatientProgramVisit(patient.getPatientProgram(), vt2);
            PatientProgramVisits ppv5 = dashboard.getPatientProgramVisit(patient.getPatientProgram(), vt5);
            PatientProgramVisits ppv8 = dashboard.getPatientProgramVisit(patient.getPatientProgram(), vt8);

            //Block 1. NEW BACTERIOLOGICALLY CONFIRMED CASES (PULMONARY)
            if (ptb && bc && np){
                report.setPbcnxTotal(report.getPbcnxTotal() + 1);

                if (hiv_pos){
                    report.setPbcnxHivPosTotal(report.getPbcnxHivPosTotal() + 1);

                    if (hiv_art){
                        report.setPbcnxHivArtTotal(report.getPbcnxHivArtTotal() + 1);
                    }

                    if (hiv_cpt){
                        report.setPbcnxHivCptTotal(report.getPbcnxHivCptTotal() + 1);
                    }
                }
                else if (hiv_neg){
                    report.setPbcnxHivNegTotal(report.getPbcnxHivNegTotal() + 1);
                }
                else if (hiv_not){
                    report.setPbcnxHivNdTotal(report.getPbcnxHivNdTotal() + 1);
                }

                //Account for only those whove completed
                if (outcome == null){
                    continue;
                }

                //Reseults At 2mnths
                if (ppv2 != null && ppv2.getSputumResult().equals(mdrtbService.getConcept(MdrtbConcepts.NOT_DONE))){
                    report.setPbcnxNotDoneTwo(report.getPbcnxNotDoneTwo() + 1);
                }
                else if (ppv2 != null && ppv2.getSputumResult().equals(mdrtbService.getConcept(MdrtbConcepts.NEGATIVE))){
                    report.setPbcnxNegativeTwo(report.getPbcnxNegativeTwo() + 1);
                }

                //Reseults At 5mnths
                if (ppv5 != null && ppv5.getSputumResult().equals(mdrtbService.getConcept(MdrtbConcepts.NOT_DONE))){
                    report.setPbcnxNotDoneFive(report.getPbcnxNotDoneFive() + 1);
                }
                else if (ppv5 != null && ppv5.getSputumResult().equals(mdrtbService.getConcept(MdrtbConcepts.NEGATIVE))){
                    report.setPbcnxNegativeFive(report.getPbcnxNegativeFive() + 1);
                }

                //Reseults At 8mnths
                if (ppv8 != null && ppv8.getSputumResult().equals(mdrtbService.getConcept(MdrtbConcepts.NOT_DONE))){
                    report.setPbcnxNotDoneEight(report.getPbcnxNotDoneEight() + 1);

                    if (hiv_pos){
                        report.setPbcnxHivPosNotDone(report.getPbcnxHivPosNotDone() + 1);

                        if (hiv_art){
                            report.setPbcnxHivArtNotDone(report.getPbcnxHivArtNotDone() + 1);
                        }

                        if (hiv_cpt){
                            report.setPbcnxHivCptNotDone(report.getPbcnxHivCptNotDone() + 1);
                        }
                    }
                    else if (hiv_neg){
                        report.setPbcnxHivNegNotDone(report.getPbcnxHivNegNotDone() + 1);
                    }
                    else if (hiv_not){
                        report.setPbcnxHivNdNotDone(report.getPbcnxHivNdNotDone() + 1);
                    }

                }
                else if (ppv8 != null && ppv8.getSputumResult().equals(mdrtbService.getConcept(MdrtbConcepts.NEGATIVE))){
                    report.setPbcnxNegativeEight(report.getPbcnxNegativeEight() + 1);

                    if (hiv_pos){
                        report.setPbcnxHivPosNegative(report.getPbcnxHivPosNegative() + 1);

                        if (patient.getArtStarted().equals(mdrtbService.getConcept(MdrtbConcepts.YES))){
                            report.setPbcnxHivArtNegative(report.getPbcnxHivArtNegative() + 1);
                        }

                        if (patient.getCptStarted().equals(mdrtbService.getConcept(MdrtbConcepts.YES))){
                            report.setPbcnxHivCptNegative(report.getPbcnxHivCptNegative() + 1);
                        }
                    }
                    else if (hiv_neg){
                        report.setPbcnxHivNegNegative(report.getPbcnxHivNegNegative() + 1);
                    }
                    else if (hiv_not){
                        report.setPbcnxHivNdNegative(report.getPbcnxHivNdNegative() + 1);
                    }
                }

                //Total Evaluated (Less Transfer Outs)
                if (outcome.equals(mdrtbService.getConcept(MdrtbConcepts.PATIENT_TRANSFERRED_OUT))){
                    report.setPbcnxEvaluated(report.getPbcnxEvaluated() + 1);

                    if (hiv_pos){
                        report.setPbcnxHivPosEvaluated(report.getPbcnxHivPosEvaluated() + 1);

                        if (hiv_art){
                            report.setPbcnxHivArtEvaluated(report.getPbcnxHivArtEvaluated() + 1);
                        }

                        if (hiv_cpt){
                            report.setPbcnxHivCptEvaluated(report.getPbcnxHivCptEvaluated() + 1);
                        }
                    }
                    else if (hiv_neg){
                        report.setPbcnxHivNegEvaluated(report.getPbcnxHivNegEvaluated() + 1);
                    }
                    else if (hiv_not){
                        report.setPbcnxHivNdEvaluated(report.getPbcnxHivNdEvaluated() + 1);
                    }
                }

                //Other Tracked Outcomes
                if (died){
                    report.setPbcnxDied(report.getPbcnxDied() + 1);

                    if (hiv_pos){
                        report.setPbcnxHivPosDied(report.getPbcnxHivPosDied() + 1);

                        if (hiv_art){
                            report.setPbcnxHivArtDied(report.getPbcnxHivArtDied() + 1);
                        }

                        if (hiv_cpt){
                            report.setPbcnxHivCptDied(report.getPbcnxHivCptDied() + 1);
                        }
                    }
                    else if (hiv_neg){
                        report.setPbcnxHivNegDied(report.getPbcnxHivNegDied() + 1);
                    }
                    else if (hiv_not){
                        report.setPbcnxHivNdDied(report.getPbcnxHivNdDied() + 1);
                    }
                }
                else if (lost){
                    report.setPbcnxLTFU(report.getPbcnxLTFU() + 1);

                    if (hiv_pos){
                        report.setPbcnxHivPosLTFU(report.getPbcnxHivPosLTFU() + 1);

                        if (hiv_art){
                            report.setPbcnxHivArtLTFU(report.getPbcnxHivArtLTFU() + 1);
                        }

                        if (hiv_cpt){
                            report.setPbcnxHivCptLTFU(report.getPbcnxHivCptLTFU() + 1);
                        }
                    }
                    else if (hiv_neg){
                        report.setPbcnxHivNegLTFU(report.getPbcnxHivNegLTFU() + 1);
                    }
                    else if (hiv_not){
                        report.setPbcnxHivNdLTFU(report.getPbcnxHivNdLTFU() + 1);
                    }
                }

            }

            //Block 2. NEW SMEAR NEGATIVE PTB CASES
            if (ptb && np && (ppv8 != null && ppv8.getSputumResult().equals(mdrtbService.getConcept(MdrtbConcepts.NEGATIVE)) ) ){

            }
        }

        model.addAttribute("report", report);
    }
}
