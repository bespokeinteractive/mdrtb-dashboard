package org.openmrs.module.mdrtbdashboard.api.impl;

import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.db.MdrtbDashboardServiceDAO;
import org.openmrs.module.mdrtbdashboard.util.DrugTestingResults;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 12/24/2016.
 */
public class MdrtbDashboardServiceImpl
        extends BaseOpenmrsService
        implements MdrtbDashboardService{
    private MdrtbDashboardServiceDAO dao;
    public MdrtbDashboardServiceDAO getDao() { return dao; }
    public void setDao(MdrtbDashboardServiceDAO dao) { this.dao = dao; }

    @Override
    public Integer getNextTbmuNumberCount(String header) {
        return dao.getNextTbmuNumberCount(header);
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


}
