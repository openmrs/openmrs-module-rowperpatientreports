package org.openmrs.module.rowperpatientreports.service.db;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.Concept;


public class HibernateRowPerPatientReportDAO implements RowPerPatientReportDAO{
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Hibernate sessionFactory.getCurrentSession() factory
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * Set sessionFactory.getCurrentSession() factory
	 * 
	 * @param sessionFactory SessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Date getBirthDate(Integer patientId) {
	    
		SQLQuery birthDateQuery = sessionFactory.getCurrentSession().createSQLQuery("select birthdate from person where person_id=:personId");
		birthDateQuery.setInteger("personId", patientId);
		
		Date birthDate = (Date)birthDateQuery.uniqueResult();
		
		return birthDate;
    }

	public Date getDateOfWorkflowStateChange(Integer patientId, Integer conceptId) {
		
		SQLQuery dateOfWorkFlowStateQuery = sessionFactory.getCurrentSession().createSQLQuery("select start_date from patient_state ps, patient_program pp, program_workflow_state pws where pp.patient_program_id = ps.patient_program_id  and pws.program_workflow_state_id = ps.state and ps.voided = 0 and pws.concept_id = :conceptId and pp.patient_id = :patientId ");
		dateOfWorkFlowStateQuery.setInteger("patientId", patientId);
		dateOfWorkFlowStateQuery.setInteger("conceptId", conceptId);
		
		List<Date> dateOfWorkflow = (List<Date>)dateOfWorkFlowStateQuery.list();
		
		//TODO: figure out what is the most logical date to return when multiples are found
		if(dateOfWorkflow != null && dateOfWorkflow.size() > 0)
		{
			return dateOfWorkflow.get(0);
		}
	    return null;
    }

	public Date getDateOfWorkflowStateChange(Integer patientId, List<Integer> conceptIds) {
		StringBuilder sql = new StringBuilder("select start_date from patient_state ps, patient_program pp, program_workflow_state pws where pp.patient_program_id = ps.patient_program_id  and pws.program_workflow_state_id = ps.state and ps.voided = 0 and pp.patient_id = :patientId and pws.concept_id in(");
		int i = 1;
		for(Integer conceptId: conceptIds)
		{
			if(i > 1)
			{
				sql.append(",");
			}
			sql.append(" :conceptId");
			sql.append(i);
			i++;
		}
		sql.append(")");
		
		SQLQuery dateOfWorkFlowStateQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		dateOfWorkFlowStateQuery.setInteger("patientId", patientId);
		i = 1;
		for(Integer conceptId: conceptIds)
		{
			String variableName = "conceptId" + i;
			i++;
			dateOfWorkFlowStateQuery.setInteger(variableName, conceptId);
		}
		
		List<Date> dateOfWorkflow = (List<Date>)dateOfWorkFlowStateQuery.list();
		
		//TODO: figure out what is the most logical date to return when multiples are found
		if(dateOfWorkflow != null && dateOfWorkflow.size() > 0)
		{
			return dateOfWorkflow.get(0);
		}
	    return null;
    }

	public Integer getObsValueBeforeDate(Integer patientId, Integer conceptId, Date compareDate) {
		
		SQLQuery obsBeforeDate = sessionFactory.getCurrentSession().createSQLQuery("select obs_id from obs where person_id = :patientId and concept_id = :conceptId and voided = 0 and obs_dateTime < :compareDate order by obs_dateTime");
		obsBeforeDate.setInteger("patientId", patientId);
		obsBeforeDate.setInteger("conceptId", conceptId);
		obsBeforeDate.setDate("compareDate", compareDate);
		
		List<Integer> obs = obsBeforeDate.list();
		if(obs != null && obs.size() > 0)
		{
			return obs.get(0);
		}
		
	    return null;
    }
	
	public Integer getObsValueBeforeDate(Integer patientId, Integer conceptId, Integer groupId, Date compareDate) {
		
		SQLQuery obsBeforeDate = sessionFactory.getCurrentSession().createSQLQuery("select o.obs_id from obs o, obs og where o.person_id = :patientId and o.concept_id = :conceptId and o.voided = 0 and o.obs_dateTime < :compareDate and o.obs_group_id = og.obs_id and og.voided = 0 and og.concept_id = :groupId order by o.obs_dateTime");
		obsBeforeDate.setInteger("patientId", patientId);
		obsBeforeDate.setInteger("conceptId", conceptId);
		obsBeforeDate.setInteger("groupId", groupId);
		obsBeforeDate.setDate("compareDate", compareDate);
		
		List<Integer> obs = obsBeforeDate.list();
		if(obs != null && obs.size() > 0)
		{
			return obs.get(0);
		}
		
	    return null;
    }

	public Integer getObsValueAfterDate(Integer patientId, Integer conceptId, Date compareDate) {
		SQLQuery obsBeforeDate = sessionFactory.getCurrentSession().createSQLQuery("select obs_id from obs where person_id = :patientId and concept_id = :conceptId and voided = 0 and obs_dateTime > :compareDate order by obs_dateTime");
		obsBeforeDate.setInteger("patientId", patientId);
		obsBeforeDate.setInteger("conceptId", conceptId);
		obsBeforeDate.setDate("compareDate", compareDate);
		
		List<Integer> obs = obsBeforeDate.list();
		if(obs != null && obs.size() > 0)
		{
			return obs.get(0);
		}
		
	    return null;
    }
	
	public Integer getObsValueAfterDate(Integer patientId, Integer conceptId, Integer groupId, Date compareDate) {
		SQLQuery obsBeforeDate = sessionFactory.getCurrentSession().createSQLQuery("select o.obs_id from obs o, obs og where o.person_id = :patientId and o.concept_id = :conceptId and o.voided = 0 and o.obs_dateTime > :compareDate and o.obs_group_id = og.obs_id and og.voided = 0 and og.concept_id = :groupId order by o.obs_dateTime");
		obsBeforeDate.setInteger("patientId", patientId);
		obsBeforeDate.setInteger("conceptId", conceptId);
		obsBeforeDate.setInteger("groupId", groupId);
		obsBeforeDate.setDate("compareDate", compareDate);
		
		List<Integer> obs = obsBeforeDate.list();
		if(obs != null && obs.size() > 0)
		{
			return obs.get(0);
		}
		
	    return null;
    }

	public Date getDateOfProgramEnrolment(Integer patientId, Integer programId) {
		SQLQuery dateOfProgramEnrolment = sessionFactory.getCurrentSession().createSQLQuery("select date_enrolled from patient_program where patient_id = :patientId and program_id = :programId and voided = 0 order by date_enrolled desc");
		dateOfProgramEnrolment.setInteger("patientId", patientId);
		dateOfProgramEnrolment.setInteger("programId", programId);
		
		List<Date> dateOfEnrolment = (List<Date>)dateOfProgramEnrolment.list();
		
		//TODO: figure out what is the most logical date to return when multiples are found
		if(dateOfEnrolment != null && dateOfEnrolment.size() > 0)
		{
			return dateOfEnrolment.get(0);
		}
	    return null;
	}
	
	public Integer getObsValueBetweenDates(Integer patientId, Integer conceptId, Date beforeDate, Date afterDate, Date targetDate) {
		SQLQuery obsBeforeDate = sessionFactory.getCurrentSession().createSQLQuery("select obs_id from obs where person_id = :patientId and concept_id = :conceptId and voided = 0 and obs_dateTime > :beforeDate and obs_dateTime < :afterDate ORDER BY abs(:targetDate - obs_dateTime)");
		obsBeforeDate.setInteger("patientId", patientId);
		obsBeforeDate.setInteger("conceptId", conceptId);
		obsBeforeDate.setDate("beforeDate", beforeDate);
		obsBeforeDate.setDate("afterDate", afterDate);
		obsBeforeDate.setDate("targetDate", targetDate);
		
		List<Integer> obs = obsBeforeDate.list();
		if(obs != null && obs.size() > 0)
		{
			return obs.get(0);
		}
		
	    return null;
    }
	
	public Integer getObsValueBetweenDates(Integer patientId, Integer conceptId, Integer groupId, Date beforeDate, Date afterDate, Date targetDate) {
		SQLQuery obsBeforeDate = sessionFactory.getCurrentSession().createSQLQuery("select o.obs_id from obs o, obs og where o.person_id = :patientId and o.concept_id = :conceptId and o.voided = 0 and o.obs_dateTime > :beforeDate and o.obs_dateTime < :afterDate and o.obs_group_id = og.obs_id and og.voided = 0 and og.concept_id = :groupId ORDER BY abs(:targetDate - obs_dateTime)");
		obsBeforeDate.setInteger("patientId", patientId);
		obsBeforeDate.setInteger("conceptId", conceptId);
		obsBeforeDate.setDate("beforeDate", beforeDate);
		obsBeforeDate.setInteger("groupId", groupId);
		obsBeforeDate.setDate("afterDate", afterDate);
		obsBeforeDate.setDate("targetDate", targetDate);
		
		List<Integer> obs = obsBeforeDate.list();
		if(obs != null && obs.size() > 0)
		{
			return obs.get(0);
		}
		
	    return null;
    }
	
	public Integer getObsAnswerBetweenDates(Integer patientId, Integer answerId, Date beforeDate, Date afterDate, Date targetDate) {
		SQLQuery obsBeforeDate = sessionFactory.getCurrentSession().createSQLQuery("select obs_id from obs where person_id = :patientId and value_coded = :conceptId and voided = 0 and obs_dateTime > :beforeDate and obs_dateTime < :afterDate ORDER BY abs(:targetDate - obs_dateTime)");
		obsBeforeDate.setInteger("patientId", patientId);
		obsBeforeDate.setInteger("conceptId", answerId);
		obsBeforeDate.setDate("beforeDate", beforeDate);
		obsBeforeDate.setDate("afterDate", afterDate);
		obsBeforeDate.setDate("targetDate", targetDate);
		
		List<Integer> obs = obsBeforeDate.list();
		if(obs != null && obs.size() > 0)
		{
			return obs.get(0);
		}
		
	    return null;
    }
	
	public Integer getObsAnswerBetweenDates(Integer patientId, List<Integer> questions, Integer answerId, Date beforeDate, Date afterDate, Date targetDate) {
		SQLQuery obsBeforeDate = sessionFactory.getCurrentSession().createSQLQuery("select obs_id from obs where person_id = :patientId and value_coded = :conceptId and concept_id in (:questions) and voided = 0 and obs_dateTime > :beforeDate and obs_dateTime < :afterDate ORDER BY abs(:targetDate - obs_dateTime)");
		obsBeforeDate.setInteger("patientId", patientId);
		obsBeforeDate.setInteger("conceptId", answerId);
		obsBeforeDate.setDate("beforeDate", beforeDate);
		obsBeforeDate.setDate("afterDate", afterDate);
		obsBeforeDate.setDate("targetDate", targetDate);
		obsBeforeDate.setParameterList("questions", questions);
		
		List<Integer> obs = obsBeforeDate.list();
		if(obs != null && obs.size() > 0)
		{
			return obs.get(0);
		}
		
	    return null;
    }
	
	public Date getDateOfProgramEnrolmentAscending(Integer patientId, Integer programId) {
		SQLQuery dateOfProgramEnrolment = sessionFactory.getCurrentSession().createSQLQuery("select date_enrolled from patient_program where patient_id = :patientId and program_id = :programId and voided = 0 order by date_enrolled asc");
		dateOfProgramEnrolment.setInteger("patientId", patientId);
		dateOfProgramEnrolment.setInteger("programId", programId);
		
		List<Date> dateOfEnrolment = (List<Date>)dateOfProgramEnrolment.list();
		
		//TODO: figure out what is the most logical date to return when multiples are found
		if(dateOfEnrolment != null && dateOfEnrolment.size() > 0)
		{
			return dateOfEnrolment.get(0);
		}
	    return null;
	}
	
	public Date getDateOfProgramCompletion(Integer patientId, Integer programId) {
		SQLQuery dateOfProgramEnrolment = sessionFactory.getCurrentSession().createSQLQuery("select date_completed from patient_program where patient_id = :patientId and program_id = :programId and voided = 0 order by date_enrolled desc");
		dateOfProgramEnrolment.setInteger("patientId", patientId);
		dateOfProgramEnrolment.setInteger("programId", programId);
		
		List<Date> dateOfEnrolment = (List<Date>)dateOfProgramEnrolment.list();
		
		//TODO: figure out what is the most logical date to return when multiples are found
		if(dateOfEnrolment != null && dateOfEnrolment.size() > 0)
		{
			return dateOfEnrolment.get(0);
		}
	    return null;
	}
	
	public Date getDateOfProgramCompletionAscending(Integer patientId, Integer programId) {
		SQLQuery dateOfProgramEnrolment = sessionFactory.getCurrentSession().createSQLQuery("select date_completed from patient_program where patient_id = :patientId and program_id = :programId and voided = 0 order by date_enrolled asc");
		dateOfProgramEnrolment.setInteger("patientId", patientId);
		dateOfProgramEnrolment.setInteger("programId", programId);
		
		List<Date> dateOfEnrolment = (List<Date>)dateOfProgramEnrolment.list();
		
		//TODO: figure out what is the most logical date to return when multiples are found
		if(dateOfEnrolment != null && dateOfEnrolment.size() > 0)
		{
			return dateOfEnrolment.get(0);
		}
	    return null;
	}
}
