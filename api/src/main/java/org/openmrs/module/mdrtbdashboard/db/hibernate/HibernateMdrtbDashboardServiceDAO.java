package org.openmrs.module.mdrtbdashboard.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.db.MdrtbDashboardServiceDAO;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresAgencies;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresRegions;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;

import java.util.*;

/**
 * Created by Dennis Henry on 12/24/2016.
 */
public class HibernateMdrtbDashboardServiceDAO implements MdrtbDashboardServiceDAO {
    protected final Log log = LogFactory.getLog(getClass());
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) throws DAOException {
        this.sessionFactory = sessionFactory;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<LocationCentres> getCentres(){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        List list = criteria.list();
        return list;
    }

    @Override
    public List<LocationCentresAgencies> getAgencies(){
        Criteria criteria = getSession().createCriteria(LocationCentresAgencies.class);
        List list = criteria.list();
        return list;
    }

    @Override
    public List<LocationCentresRegions> getRegions(){
        Criteria criteria = getSession().createCriteria(LocationCentresRegions.class);
        List list = criteria.list();
        return list;
    }

    @Override
    public LocationCentres getCentresByLocation(Location location){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        criteria.add(Restrictions.eq("location", location));
        return (LocationCentres) criteria.uniqueResult();
    }

    @Override
    public LocationCentres saveLocationCentres(LocationCentres centre){
        return (LocationCentres)getSession().merge(centre);
    }

    @Override
    public LocationCentresAgencies getAgency(Integer agentId){
        Criteria criteria = getSession().createCriteria(LocationCentresAgencies.class);
        criteria.add(Restrictions.eq("id", agentId));
        return (LocationCentresAgencies) criteria.uniqueResult();
    }

    @Override
    public LocationCentresRegions getRegion(Integer regionId){
        Criteria criteria = getSession().createCriteria(LocationCentresRegions.class);
        criteria.add(Restrictions.eq("id", regionId));
        return (LocationCentresRegions) criteria.uniqueResult();
    }

    @Override
    public PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram){
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));
        return (PatientProgramDetails) criteria.uniqueResult();
    }

    @Override
    public Integer getNextTbmuNumberCount(String header) {
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.add(Restrictions.like("tbmuNumber", header, MatchMode.START));
        return criteria.list().size() + 1;
    }

    @Override
    public PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails) {
        return (PatientProgramDetails)getSession().merge(patientProgramDetails);
    }

    @Override
    public List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId){
        Vector search = new Vector();

        String[] searchSplit = nameOrIdentifier.split("\\s+");
        String hql = "SELECT DISTINCT p.patient_id,pi.identifier,pn.given_name ,pn.middle_name ,pn.family_name ,ps.gender,ps.birthdate,ps.dead,0, pn.person_name_id ,EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) age FROM patient p INNER JOIN person ps ON p.patient_id = ps.person_id INNER JOIN patient_identifier PI ON p.patient_id = pi.patient_id INNER JOIN person_name pn ON p.patient_id = pn.person_id INNER JOIN encounter en ON p.patient_id = en.patient_id LEFT OUTER JOIN patient_program pp ON pp.patient_id=p.patient_id LEFT OUTER JOIN patient_program_details ppd ON pp.patient_program_id=ppd.patient_program_id WHERE (ppd.tbmu_number like \'%" + nameOrIdentifier + "%\'";

        if(searchSplit.length == 1) {
            String[] query = searchSplit;
            int l = searchSplit.length;

            for(int i$ = 0; i$ < l; ++i$) {
                String obj = query[i$];
                hql = hql + "OR pn.given_name like \'%" + obj + "%\' " + "OR pn.middle_name like \'%" + obj + "%\' " + "OR pn.family_name like \'%" + obj + "%\' ";
            }
        } else if(searchSplit.length == 2) {
            hql = hql + "OR pn.given_name like \'%" + searchSplit[0] + "%\' " + "AND (pn.middle_name like \'%" + searchSplit[1] + "%\' " + "OR pn.family_name like \'%" + searchSplit[1] + "%\') ";
        } else if(searchSplit.length == 3) {
            hql = hql + "OR pn.given_name like \'%" + searchSplit[0] + "%\' " + "AND pn.middle_name like \'%" + searchSplit[2] + "%\' " + "AND pn.family_name like \'%" + searchSplit[1] + "%\'";
        }

        hql += ") ";

        if(StringUtils.isNotBlank(gender)) {
            hql = hql + " AND ps.gender = \'" + gender + "\' ";
        }

        if(age > 0) {
            hql = hql + " AND EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) BETWEEN " + (age - rangeAge) + " AND " + (age + rangeAge) + " ";
        }

        if(StringUtils.isNotBlank(lastDayOfVisit)) {
            hql = hql + " AND (DATE_FORMAT(DATE(en.encounter_datetime),\'%Y/%m/%d\') = \'" + lastDayOfVisit + "\')";
        }

        if(lastVisit > 0) {
            hql = hql + " AND (DATEDIFF(NOW(), en.date_created) <= " + lastVisit + ")";
        }

        hql += " ORDER BY pn.given_name ASC, pn.family_name ASC, pn.middle_name ASC LIMIT 0, 50";

        SQLQuery sqlquery = this.sessionFactory.getCurrentSession().createSQLQuery(hql);
        List listquery = sqlquery.list();
        if(CollectionUtils.isNotEmpty(listquery)) {
            Iterator iterator = listquery.iterator();

            while(iterator.hasNext()) {
                Object o = iterator.next();
                Object[] obss = (Object[])((Object[])o);
                if(obss != null && obss.length > 0) {
                    Person person = new Person((Integer)obss[0]);
                    PersonName personName = new PersonName((Integer)obss[9]);
                    personName.setGivenName((String)obss[2]);
                    personName.setMiddleName((String)obss[3]);
                    personName.setFamilyName((String)obss[4]);
                    personName.setPerson(person);
                    HashSet names = new HashSet();
                    names.add(personName);
                    person.setNames(names);
                    Patient patient = new Patient(person);
                    PatientIdentifier patientIdentifier = new PatientIdentifier();
                    patientIdentifier.setPatient(patient);
                    patientIdentifier.setIdentifier((String)obss[1]);
                    HashSet identifier = new HashSet();
                    identifier.add(patientIdentifier);
                    patient.setIdentifiers(identifier);
                    patient.setGender((String)obss[5]);
                    patient.setBirthdate((Date)obss[6]);
                    if(obss[7] != null) {
                        if(obss[7].toString().equals("1")) {
                            patient.setDead(Boolean.valueOf(true));
                        } else if(obss[7].toString().equals("0")) {
                            patient.setDead(Boolean.valueOf(false));
                        }
                    }

                    if(obss[8] != null) {
                        if(obss[8].toString().equals("1")) {
                            patient.setVoided(Boolean.valueOf(true));
                        } else if(obss[8].toString().equals("0")) {
                            patient.setVoided(Boolean.valueOf(false));
                        }
                    }

                    MdrtbPatientProgram pp = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
                    if (programId != 0){
                        if (pp.getPatientProgram() != null && pp.getActive() && pp.getPatientProgram().getProgram() == Context.getProgramWorkflowService().getProgram(programId)){
                            search.add(pp);
                        }
                    }
                    else{
                        search.add(pp);
                    }
                }
            }
        }

        return search;
    }
}
