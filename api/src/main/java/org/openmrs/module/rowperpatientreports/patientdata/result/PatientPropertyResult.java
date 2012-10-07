package org.openmrs.module.rowperpatientreports.patientdata.result;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class PatientPropertyResult extends BasePatientDataResult {
	
	private Object value;
	private Class<?> returnClass;
	
	public PatientPropertyResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
		return returnClass;
	}
	
	public void setClass(Class<?> returnClass)
	{
		this.returnClass = returnClass;
	}
	
	public Object getValue() {
		return value;
	}
	
	public boolean isMultiple() {
		return false;
	}

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
    	this.value = value;
    }
    
   
    public String getValueAsString() {
	    return value.toString();
    }
}
