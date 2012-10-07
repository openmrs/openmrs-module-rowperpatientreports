package org.openmrs.module.rowperpatientreports.service.db;

import java.util.Date;
import java.util.List;

import org.openmrs.ProgramWorkflow;


public interface RowPerPatientReportDAO {
	
	public Date getBirthDate(Integer patientId);
	
	public Date getDateOfWorkflowStateChange(Integer patientId, Integer stateId);
	
	public Date getDateOfProgramEnrolment(Integer patientId, Integer programId);
	
	public Date getDateOfProgramEnrolmentAscending(Integer patientId, Integer programId);
	
	public Date getDateOfWorkflowStateChange(Integer patientId, List<Integer> stateIds);
	
	public Integer getObsValueBeforeDate(Integer patientId, Integer conceptId, Date compareDate);
	
	public Integer getObsValueBeforeDate(Integer patientId, Integer conceptId, Integer groupId, Date compareDate);
	
	public Integer getObsValueAfterDate(Integer patientId, Integer conceptId, Date compareDate);
	
	public Integer getObsValueAfterDate(Integer patientId, Integer conceptId, Integer groupId, Date compareDate);
	
    public Date getDateOfProgramCompletion(Integer patientId, Integer programId);
	
	public Date getDateOfProgramCompletionAscending(Integer patientId, Integer programId);
	
	public Integer getObsValueBetweenDates(Integer patientId, Integer conceptId, Date beforeDate, Date afterDate);
	
	public Integer getObsValueBetweenDates(Integer patientId, Integer conceptId, Integer groupId, Date beforeDate, Date afterDate);

}
