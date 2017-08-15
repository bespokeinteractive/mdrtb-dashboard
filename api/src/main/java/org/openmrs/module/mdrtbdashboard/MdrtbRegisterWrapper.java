package org.openmrs.module.mdrtbdashboard;

import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramVisits;
import org.openmrs.module.mdrtbdashboard.util.RegisterDrVisits;
import org.openmrs.module.mdrtbdashboard.util.RegisterTbVisits;

import java.util.Date;
import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 8/5/2017.
 */
public class MdrtbRegisterWrapper extends MdrtbPatientWrapper implements java.io.Serializable {
    private RegisterTbVisits tbVisits;
    private RegisterDrVisits drVisits;

    public MdrtbRegisterWrapper(){
        super();
    }

    public MdrtbRegisterWrapper(MdrtbPatientProgram patientProgram){
        super(patientProgram);
        this.getVisitDetails();
    }

    public MdrtbRegisterWrapper(MdrtbPatientProgram patientProgram, Date visitDate){
        super(patientProgram, visitDate);
        this.getVisitDetails();
    }

    public void getVisitDetails(){
        List<PatientProgramVisits> visits = dashboardService.getPatientProgramVisits(this.getPatientProgram().getPatientProgram());
        if (this.getPatientProgram().getPatientProgram().getProgram().getId() == 1){
            this.tbVisits = new RegisterTbVisits();

            for (PatientProgramVisits visit: visits){
                if (visit.getVisitType().getName().equals("Before Treatment")){
                    tbVisits.setDateZero(this.returnFormattedDate(visit.getExamDate()));
                    tbVisits.setResultZero(returnShortenedConceptName(visit.getSputumResult()));
                    tbVisits.setHivZero(returnShortenedConceptName(visit.getHivResults()));
                    tbVisits.setXrayZero(returnShortenedConceptName(visit.getXrayResults()));
                }
                else if(visit.getVisitType().getName().equals("2-3 Months")){
                    tbVisits.setDateTwo(this.returnFormattedDate(visit.getExamDate()));
                    tbVisits.setResultTwo(returnShortenedConceptName(visit.getSputumResult()));
                }
                else if(visit.getVisitType().getName().equals("5 Months")){
                    tbVisits.setDateFive(this.returnFormattedDate(visit.getExamDate()));
                    tbVisits.setResultFive(returnShortenedConceptName(visit.getSputumResult()));
                }
                else {
                    tbVisits.setDateFinal(this.returnFormattedDate(visit.getExamDate()));
                    tbVisits.setResultFinal(returnShortenedConceptName(visit.getSputumResult()));
                }
            }
        }
        else {
            this.drVisits = new RegisterDrVisits();

            for (PatientProgramVisits visit: visits){
                if(visit.getVisitType().getName().equals("Before Treatment")) {
                    this.drVisits.setXpertZero(this.returnShortenedConceptName(visit.getGeneXpertResult()));
                    this.drVisits.setSmearZero(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureZero(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 01")) {
                    this.drVisits.setSmearOne(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureOne(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 02")) {
                    this.drVisits.setSmearTwo(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureTwo(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 03")) {
                    this.drVisits.setSmearThree(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureThree(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 04")) {
                    this.drVisits.setSmearFour(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureFour(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 05")) {
                    this.drVisits.setSmearFive(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureFive(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 06")) {
                    this.drVisits.setSmearSix(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureSix(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 07")) {
                    this.drVisits.setSmearSeven(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureSeven(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 08")) {
                    this.drVisits.setSmearEight(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureEight(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 09")) {
                    this.drVisits.setSmearNine(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureNine(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 10")) {
                    this.drVisits.setSmearTen(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureTen(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 11")) {
                    this.drVisits.setSmearEleven(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureEleven(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 12")) {
                    this.drVisits.setSmearTwelve(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureTwelve(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 13")) {
                    this.drVisits.setSmearThirteen(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureThirteen(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 14")) {
                    this.drVisits.setSmearFourteen(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureFourteen(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 15")) {
                    this.drVisits.setSmearFifteen(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureFifteen(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 16")) {
                    this.drVisits.setSmearSixteen(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureSixteen(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 17")) {
                    this.drVisits.setSmearSeventeen(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureSeventeen(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 18")) {
                    this.drVisits.setSmearEighteen(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureEighteen(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 19")) {
                    this.drVisits.setSmearNineteen(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureNineteen(this.returnShortenedConceptName(visit.getCultureResults()));
                }
                else if(visit.getVisitType().getName().equals("MONTH 20")) {
                    this.drVisits.setSmearTwenty(this.returnShortenedConceptName(visit.getSputumResult()));
                    this.drVisits.setCultureTwenty(this.returnShortenedConceptName(visit.getCultureResults()));
                }
            }
        }
    }

    public RegisterTbVisits getTbVisits() {
        return tbVisits;
    }

    public void setTbVisits(RegisterTbVisits tbVisits) {
        this.tbVisits = tbVisits;
    }

    public RegisterDrVisits getDrVisits() {
        return drVisits;
    }

    public void setDrVisits(RegisterDrVisits drVisits) {
        this.drVisits = drVisits;
    }
}
