package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Patient;
import org.openmrs.module.reporting.evaluation.Definition;


public interface RowPerPatientData extends Definition {
	
	public Integer getPatientId();
	public void setPatientId(Integer patient);
	
	public Patient getPatient();
	public void setPatient(Patient patient);
	
	public String getDateFormat();
	public void setDateFormat(String dateFormat);
	
}
