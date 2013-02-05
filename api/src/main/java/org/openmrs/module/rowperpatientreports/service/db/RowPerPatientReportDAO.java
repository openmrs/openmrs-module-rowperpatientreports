package org.openmrs.module.rowperpatientreports.service.db;

import java.util.Date;
import java.util.List;


public interface RowPerPatientReportDAO {
	
	public Date getBirthDate(Integer patientId);
	
	public Date getDateOfWorkflowStateChange(Integer patientId, Integer stateId, Date startDate, Date endDate);
	
	public Date getDateOfProgramEnrolment(Integer patientId, Integer programId, Date startDate, Date endDate);
	
	public Date getDateOfProgramEnrolmentAscending(Integer patientId, Integer programId, Date startDate, Date endDate);
	
	public Date getDateOfWorkflowStateChange(Integer patientId, List<Integer> stateIds, Date startDate, Date endDate);
	
	public Integer getObsValueBeforeDate(Integer patientId, Integer conceptId, Date compareDate);
	
	public Integer getObsValueBeforeDate(Integer patientId, Integer conceptId, Integer groupId, Date compareDate);
	
	public Integer getObsValueAfterDate(Integer patientId, Integer conceptId, Date compareDate);
	
	public Integer getObsValueAfterDate(Integer patientId, Integer conceptId, Integer groupId, Date compareDate);
	
    public Date getDateOfProgramCompletion(Integer patientId, Integer programId);
	
	public Date getDateOfProgramCompletionAscending(Integer patientId, Integer programId);
	
	public Integer getObsValueBetweenDates(Integer patientId, Integer conceptId, Date beforeDate, Date afterDate, Date targetDate);
	
	public Integer getObsValueBetweenDates(Integer patientId, Integer conceptId, Integer groupId, Date beforeDate, Date afterDate, Date targetDate);

	public Integer getObsAnswerBetweenDates(Integer patientId, List<Integer> questions, Integer answerId, Date beforeDate, Date afterDate, Date targetDate);
	
	public Integer getEncounterBetweenDates(Integer patientId, List<Integer> encounterTypes, Date beforeDate, Date afterDate, Date targetDate);
	
	public Integer getObsAnswerBetweenDates(Integer patientId, Integer answerId, Date beforeDate, Date afterDate, Date targetDate);
}
