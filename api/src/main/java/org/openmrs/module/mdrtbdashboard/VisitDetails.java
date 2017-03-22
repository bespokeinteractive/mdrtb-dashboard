package org.openmrs.module.mdrtbdashboard;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

/**
 * Created by Dennis Henry on 1/31/2017.
 */
public class VisitDetails {
    private Double weight = 0.0;
    private Double height = 0.0;
    private Double bmi = 0.0;
    private Double muac = 0.0;

    private String facility = "";
    private String sputumSmear = "";
    private String genXpert = "";
    private String culture = "";
    private String hivExam = "";
    private String xrayExam = "";
    private String drugTest = "";
    private String location = "";
    private String date = "";
    private String labNumber = "";
    private String artStarted = "";
    private String cptStarted = "";
    private Integer showTests = 1;

    public String getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(String labNumber) {
        this.labNumber = labNumber;
    }

    public Integer getShowTests() {
        return showTests;
    }

    public void setShowTests(Integer showTests) {
        this.showTests = showTests;
    }

    public String getCptStarted() {
        return cptStarted;
    }

    public void setCptStarted(String cptStarted) {
        this.cptStarted = cptStarted;
    }

    public String getArtStarted() {
        return artStarted;
    }

    public void setArtStarted(String artStarted) {
        this.artStarted = artStarted;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getHeight() {
        return height;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setMuac(Double muac) {
        this.muac = muac;
    }

    public Double getMuac() {
        return muac;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getFacility() {
        return facility;
    }

    public void setSputumSmear(String sputumSmear) {
        this.sputumSmear = sputumSmear;
    }

    public String getSputumSmear() {
        return sputumSmear;
    }

    public void setGenXpert(String genXpert) {
        this.genXpert = genXpert;
    }

    public String getGenXpert() {
        return genXpert;
    }

    public void setHivExam(String hivExam) {
        this.hivExam = hivExam;
    }

    public String getHivExam() {
        return hivExam;
    }

    public void setXrayExam(String xrayExam) {
        this.xrayExam = xrayExam;
    }

    public String getXrayExam() {
        return xrayExam;
    }

    public String getCulture() { return culture; }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getDrugTest() { return drugTest; }

    public void setDrugTest(String drugTest) {
        this.drugTest = drugTest;
    }

    public static VisitDetails create(Encounter encounter) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        VisitDetails visitDetail = new VisitDetails();

        visitDetail.setDate(df.format(encounter.getEncounterDatetime()));
        visitDetail.setLocation(encounter.getLocation().getName());

        Concept heightConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEIGHT);
        Concept weightConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.WEIGHT);
        Concept bmiConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BMI);
        Concept muacConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MUAC);
        Concept facilityConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEALTH_FACILITY);
        Concept labNumberConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LAB_TEST_SERIAL_NUMBER);

        Concept sputumResultConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SMEAR_RESULT);
        Concept sputumDateConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SPUTUM_COLLECTION_DATE);

        Concept genXpertResultConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.GENXPERT_RESULTS);
        Concept genXpertDateConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.GENXPERT_DATE);

        Concept xrayResultConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.XRAY_RESULTS);
        Concept xrayDateConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.XRAY_DATE);
        Concept hivExamResultConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESULT_OF_HIV_TEST);
        Concept hivExamDateConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HIV_EXAM_DATE);
        Concept cultureResultConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CULTURE_RESULT);
        Concept cultureDateConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CULTURE_DATE);
        Concept drugSensitivityConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DST_EXAM);
        Concept drugSensitivityDateConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DST_DATE);

        Concept artStartConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ART_STARTED);
        Concept artStartDateConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ART_STARTED_ON);
        Concept cptStartConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CPT_STARTED);
        Concept cptStartDateConcept = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CPT_STARTED_ON);

        String sputumDate = "";
        String sputumResult = "";

        String genXpertDate = "";
        String genXpertResult = "";
        String xrayDate = "";
        String xrayResult = "";
        String hivExamDate = "";
        String hivExamResult = "";
        String cultureResult = "";
        String cultureDate = "";
        String dstResult = "";
        String dstDate = "";

        String artStarted = "";
        String artStartedOn = "";
        String cptStarted = "";
        String cptStartedOn = "";


        for (Obs obs :encounter.getAllObs()) {
            if (obs.getConcept().equals(heightConcept)) {
                visitDetail.setHeight(obs.getValueNumeric());
            }
            if (obs.getConcept().equals(weightConcept)) {
                visitDetail.setWeight(obs.getValueNumeric());
            }
            if (obs.getConcept().equals(bmiConcept)) {
                visitDetail.setBmi(obs.getValueNumeric());
            }
            if (obs.getConcept().equals(muacConcept)) {
                visitDetail.setMuac(obs.getValueNumeric());
            }
            if (obs.getConcept().equals(facilityConcept)) {
                visitDetail.setFacility(obs.getValueText());
            }
            if (obs.getConcept().equals(labNumberConcept)) {
                visitDetail.setLabNumber(obs.getValueText());
            }

            //Complex Obs (Sputum Smear)
            if (obs.getConcept().equals(sputumResultConcept)) {
                sputumResult = obs.getValueCoded().getDisplayString();
            }
            if (obs.getConcept().equals(sputumDateConcept)) {
                sputumDate = df.format(sdf.parse(obs.getValueText(), new ParsePosition(0))) ;
            }

            //Complex Obs (GenXpert)
            if (obs.getConcept().equals(genXpertResultConcept)) {
                genXpertResult = obs.getValueCoded().getDisplayString();
            }
            if (obs.getConcept().equals(genXpertDateConcept)) {
                genXpertDate = df.format(sdf.parse(obs.getValueText(), new ParsePosition(0))) ;
            }

            //Complex Obs (Xray)
            if (obs.getConcept().equals(xrayResultConcept)) {
                xrayResult = obs.getValueCoded().getDisplayString();
            }
            if (obs.getConcept().equals(xrayDateConcept)) {
                xrayDate = df.format(sdf.parse(obs.getValueText(), new ParsePosition(0))) ;
            }

            //Complex Obs (HIV)
            if (obs.getConcept().equals(hivExamResultConcept)) {
                hivExamResult = obs.getValueCoded().getDisplayString();
            }
            if (obs.getConcept().equals(hivExamDateConcept)) {
                hivExamDate = df.format(sdf.parse(obs.getValueText(), new ParsePosition(0))) ;
            }

            //Complex Obs (CULTURE)
            if (obs.getConcept().equals(cultureResultConcept)) {
                cultureResult = obs.getValueCoded().getDisplayString();
            }
            if (obs.getConcept().equals(cultureDateConcept)) {
                cultureDate = df.format(sdf.parse(obs.getValueText(), new ParsePosition(0))) ;
            }

            //Complex Obs (DST)
            if (obs.getConcept().equals(drugSensitivityConcept)) {
                dstResult = "DONE";
            }
            if (obs.getConcept().equals(drugSensitivityDateConcept)) {
                dstDate = df.format(sdf.parse(obs.getValueText(), new ParsePosition(0))) ;
            }

            //Complex Obs (ART)
            if (obs.getConcept().equals(artStartConcept)) {
                artStarted = "ART Started";
            }
            if (obs.getConcept().equals(artStartDateConcept)) {
                artStartedOn = df.format(sdf.parse(obs.getValueText(), new ParsePosition(0))) ;
            }

            //Complex Obs (CPT)
            if (obs.getConcept().equals(cptStartConcept)) {
                cptStarted = "CPT Started";
            }
            if (obs.getConcept().equals(cptStartDateConcept)) {
                cptStartedOn = df.format(sdf.parse(obs.getValueText(), new ParsePosition(0))) ;
            }
        }

        // Merge Complex Obs (Sputum Smear)
        if (!sputumResult.equals("NOT DONE") && !sputumResult.equals("")){
            sputumResult += " (tested on " + sputumDate + ")";
        }

        // Merge Complex Obs (GENXPERT)
        if (!genXpertResult.equals("NOT DONE") && !genXpertResult.equals("")){
            genXpertResult += " (tested on " + genXpertDate + ")";
        }

        // Merge Complex Obs (XRAY)
        if (!xrayResult.equals("NOT DONE") && !xrayResult.equals("")){
            xrayResult += " (tested on " + xrayDate + ")";
        }

        // Merge Complex Obs (CULTURE)
        if (!cultureResult.equals("NOT DONE") && !cultureResult.equals("")){
            cultureResult += " (performed on " + cultureDate + ")";
        }

        // Merge Complex Obs (DST)
        if (!dstResult.equals("NOT DONE") && !dstResult.equals("")){
            dstResult = "Done on " + dstDate;
        }

        // Merge Complex Obs (HIV)
        if (!hivExamResult.equals("NOT DONE") && !hivExamResult.equals("")){
            hivExamResult += " (tested on " + hivExamDate + ")";
        }

        // Merge Complex Obs (ART)
        if (artStarted.equals("ART Started") && !artStartedOn.equals("")){
            artStarted += " on " + artStartedOn;
        }

        // Merge Complex Obs (CPT)
        if (cptStarted.equals("CPT Started") && !cptStartedOn.equals("")){
            cptStarted += " on " + cptStartedOn;
        }

        // Indicate Whether Exams should be displayed
        if (sputumResult.equals("") && genXpertResult.equals("") && xrayResult.equals("") && hivExamResult.equals("") && cultureResult.equals("") && dstResult.equals("")){
            visitDetail.setShowTests(0);
        }
        else{
            visitDetail.setShowTests(1);
        }

        //Set Complex Obs
        visitDetail.setGenXpert(genXpertResult);
        visitDetail.setXrayExam(xrayResult);
        visitDetail.setHivExam(hivExamResult);
        visitDetail.setCulture(cultureResult);
        visitDetail.setDrugTest(dstResult);
        visitDetail.setSputumSmear(sputumResult);
        visitDetail.setArtStarted(artStarted);
        visitDetail.setCptStarted(cptStarted);

        return visitDetail;
    }
}
