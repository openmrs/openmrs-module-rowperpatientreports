package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Patient;
import org.openmrs.module.reporting.evaluation.BaseDefinition;


public class BasePatientData extends BaseDefinition implements RowPerPatientData {

	private Integer id;
	private Integer patientId;
	private Patient patient;
	
	private String dateFormat = "yyyy/MM/dd";
	
	public Integer getId() {
	   return id;
    }

	public void setId(Integer id) {
	    this.id = id;
    }

	
    /**
     * @return the patient
     */
    public Integer getPatientId() {
    	return patientId;
    }

	
    /**
     * @param patient the patient to set
     */
    public void setPatientId(Integer patientId) {
    	this.patientId = patientId;
    }

	
    /**
     * @return the patient
     */
    public Patient getPatient() {
    	return patient;
    }

	
    /**
     * @param patient the patient to set
     */
    public void setPatient(Patient patient) {
    	this.patient = patient;
    }

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

}
