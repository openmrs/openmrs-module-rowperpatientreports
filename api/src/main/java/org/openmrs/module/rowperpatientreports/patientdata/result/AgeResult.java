package org.openmrs.module.rowperpatientreports.patientdata.result;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class AgeResult extends BasePatientDataResult {
	
	private Integer value;
	
	public AgeResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
		return Integer.class;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public boolean isMultiple() {
		return false;
	}

    /**
     * @param value the value to set
     */
    public void setValue(Integer value) {
    	this.value = value;
    }

	
    public String getValueAsString() {
    	if(getValue() != null)
    	{
    		return getValue().toString();
    	}
    	return null;
    }
}
