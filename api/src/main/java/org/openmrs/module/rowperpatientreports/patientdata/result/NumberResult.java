package org.openmrs.module.rowperpatientreports.patientdata.result;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;

public class NumberResult extends BasePatientDataResult {
	
	private Number value;
	
	public NumberResult(RowPerPatientData patientData, EvaluationContext ec) {
		super(patientData, ec);
		}

	public Class<?> getColumnClass() {
		return Number.class;
	}

	public Number getValue() {
		return value;
	}

	public boolean isMultiple() {
		return false;
	}

	public String getValueAsString() {
		if(value != null)
		{
			return value.toString();
		}
		
		return "";
	}

	public void setValue(Number value) {
		this.value = value;
	}

}
