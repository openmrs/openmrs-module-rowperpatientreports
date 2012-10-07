package org.openmrs.module.rowperpatientreports.patientdata.result;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class StringResult extends BasePatientDataResult {
	
	private String value;
	
	public StringResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
		return String.class;
	}
	
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
    public String getValueAsString() {
	    return value;
    }

	public boolean isMultiple() {
		return false;
	}
}
