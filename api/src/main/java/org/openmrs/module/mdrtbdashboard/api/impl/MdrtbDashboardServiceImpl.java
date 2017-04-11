package org.openmrs.module.mdrtbdashboard.api.impl;

import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.util.DrugSensitivityModel;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.db.MdrtbDashboardServiceDAO;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresAgencies;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresRegions;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.util.DrugTestingResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis Henry on 12/24/2016.
 */
public class MdrtbDashboardServiceImpl
        extends BaseOpenmrsService
        implements MdrtbDashboardService{
    private MdrtbDashboardServiceDAO dao;
    public MdrtbDashboardServiceDAO getDao() { return dao; }
    public void setDao(MdrtbDashboardServiceDAO dao) { this.dao = dao; }

    @Override
    public List<LocationCentres> getCentres(){
        return dao.getCentres();
    }

    @Override
    public List<LocationCentresAgencies> getAgencies(){
        return dao.getAgencies();
    }

    @Override
    public List<LocationCentresRegions> getRegions(){
        return dao.getRegions();
    }

    @Override
    public LocationCentres getCentresByLocation(Location location){
        return dao.getCentresByLocation(location);
    }

    @Override
    public LocationCentres saveLocationCentres(LocationCentres centre){
        return dao.saveLocationCentres(centre);
    }

    @Override
    public LocationCentresAgencies getAgency(Integer agentId){
        return dao.getAgency(agentId);
    }

    @Override
    public LocationCentresRegions getRegion(Integer regionId){
        return dao.getRegion(regionId);
    }

    @Override
    public PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram){
        return dao.getPatientProgramDetails(patientProgram);
    }

    @Override
    public Integer getNextTbmuNumberCount(String header) {
        return dao.getNextTbmuNumberCount(header);
    }

    @Override
    public PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails) {
        return dao.savePatientProgramDetails(patientProgramDetails);
    }

    @Override
    public List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId){
        return dao.getMdrtbPatients(nameOrIdentifier, gender, age, rangeAge, lastDayOfVisit, lastVisit, programId);
    }

    @Override
    public List<DrugTestingResults> getDrugSensitivityOutcome(Encounter encounter){
        List<DrugTestingResults> outcomes = new ArrayList<DrugTestingResults>();

        for (Obs obs : encounter.getAllObs()){
            if (obs.getObsGroup() == null || obs.getValueCoded() == null){
                continue;
            }

            if (obs.getObsGroup().getConcept().equals(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DST_EXAM)) ){
                try {
                    DrugTestingResults drugTest = new DrugTestingResults();
                    drugTest.setDrugName(obs.getConcept().getName().toString());
                    drugTest.setTestResult(obs.getValueCoded().getName().toString());

                    outcomes.add(drugTest);
                }
                catch (Exception e){
                    System.out.println("ERROR");
                }
            }
        }

        return outcomes;
    }
}
