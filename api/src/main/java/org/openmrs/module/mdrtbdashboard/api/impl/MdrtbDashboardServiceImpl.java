package org.openmrs.module.mdrtbdashboard.api.impl;

import org.openmrs.Location;
import org.openmrs.PatientProgram;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.db.MdrtbDashboardServiceDAO;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresAgencies;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;

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
    public LocationCentres getCentresByLocation(Location location){
        return dao.getCentresByLocation(location);
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
}
