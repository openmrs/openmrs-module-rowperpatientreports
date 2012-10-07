package org.openmrs.module.rowperpatientreports.patientdata.result;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class PatientAttributeResult extends BasePatientDataResult {
	
	private String value;
	
	public PatientAttributeResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
		return String.class;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isMultiple() {
		return false;
	}

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
    	this.value = value;
    }

	
    public String getValueAsString() {
	    return value;
    }
}
