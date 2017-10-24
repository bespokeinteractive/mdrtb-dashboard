package org.openmrs.module.mdrtbdashboard.api.impl;

import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.db.MdrtbDashboardServiceDAO;
import org.openmrs.module.mdrtbdashboard.model.*;
import org.openmrs.module.mdrtbdashboard.util.DrugTestingResults;

import java.util.ArrayList;
import java.util.Date;
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
    public LocationFacilities saveLocationFacilities(LocationFacilities facility){
        return dao.saveLocationFacilities(facility);
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
    public PatientProgramDetails getPatientProgramDetails(PatientProgram pp){
        return dao.getPatientProgramDetails(pp);
    }

    @Override
    public PatientProgramDetails getPatientProgramDetails(MdrtbPatientProgram mpp){
        return getPatientProgramDetails(mpp.getPatientProgram());
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
    public PatientProgramDetails saveParentProgramOutcome(PatientProgramDetails ppd, Concept outcome, Date completedOn){
        return dao.saveParentProgramOutcome(ppd, outcome, completedOn);
    }

    @Override
    public PatientProgramVisits savePatientProgramVisits(PatientProgramVisits patientProgramVisit){
        return dao.savePatientProgramVisits(patientProgramVisit);
    }

    @Override
    public RegimentType getRegimenType(Concept concept, Program program){
        return dao.getRegimenType(concept, program);
    }

    @Override
    public List<RegimentType> getRegimenTypes(Concept concept, Program program){
        return dao.getRegimenTypes(concept, program);
    }

    @Override
    public List<VisitTypes> getVisitTypes(Program program, Boolean initial, Boolean finals, Boolean voided){
        return dao.getVisitTypes(program, initial, finals, voided);
    }

    @Override
    public VisitTypes getVisitType(Program program, String name){
        return dao.getVisitType(program, name);
    }

    @Override
    public VisitTypes getVisitType(Integer id){
        return dao.getVisitType(id);
    }

    @Override
    public PatientProgramVisits getPatientProgramVisit(PatientProgram patientProgram, VisitTypes visitType){
        return dao.getPatientProgramVisit(patientProgram, visitType);
    }

    @Override
    public PatientProgramVisits getPatientProgramVisit(Encounter encounter){
        return dao.getPatientProgramVisit(encounter);
    }

    @Override
    public List<PatientProgramVisits> getPatientProgramVisits(PatientProgram patientProgram){
        return dao.getPatientProgramVisits(patientProgram);
    }

    @Override
    public PatientProgramDetails getPatientProgramDetails(Integer ppid){
        return dao.getPatientProgramDetails(ppid);
    }

    @Override
    public List<PatientProgramRegimen> getPatientProgramRegimens(PatientProgramDetails pd, Boolean active){
        return dao.getPatientProgramRegimens(pd, active);
    }

    @Override
    public PatientProgramRegimen savePatientProgramRegimen(PatientProgramRegimen patientProgramRegimen){
        return dao.savePatientProgramRegimen(patientProgramRegimen);
    }

    @Override
    public List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations){
        return dao.getMdrtbPatients(nameOrIdentifier, gender, age, rangeAge, lastDayOfVisit, lastVisit, programId, locations);
    }

    @Override
    public List<MdrtbPatientProgram> getMdrtbPatients(String gender, int age, int rangeAge, int programId, List<Location> locations){
        return dao.getMdrtbPatients(gender, age, rangeAge, programId, locations);
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

    @Override
    public List<LocationFacilities> getFacilities(Location location, String status){
        return dao.getFacilities(location, status);
    }

    @Override
    public List<PatientProgramDetails> getPatientsFromDetails(Location location, Date startDate, Date endDate, LocationFacilities facility){
        return dao.getPatientsFromDetails(location, startDate, endDate, facility);
    }

    @Override
    public LocationFacilities getFacilityById(Integer facilityId){
        return dao.getFacilityById(facilityId);
    }

    @Override
    public List<PatientProgramTransfers> getActivePatientTransfers(PatientProgram patientProgram){
        return dao.getActivePatientTransfers(patientProgram);
    }

    @Override
    public List <PatientProgramTransfers> getPatientProgramTransfers(Location location, Boolean status){
        return  dao.getPatientProgramTransfers(location, status);
    }

    @Override
    public PatientProgramTransfers getPatientProgramTransfers(Integer transferId){
        return dao.getPatientProgramTransfers(transferId);
    }

    @Override
    public PatientProgramTransfers savePatientProgramTransfers(PatientProgramTransfers patientProgramTransfers){
        return dao.savePatientProgramTransfers(patientProgramTransfers);
    }
}
