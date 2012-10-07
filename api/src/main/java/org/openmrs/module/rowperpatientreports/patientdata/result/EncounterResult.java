package org.openmrs.module.rowperpatientreports.patientdata.result;

import java.text.SimpleDateFormat;

import org.openmrs.Encounter;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class EncounterResult extends BasePatientDataResult {
	
	private Encounter value;
	
	private String dateFormat = "yyyy-MM-dd";
	
	public EncounterResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
		return String.class;
	}
	
	public Encounter getValue() {
		return value;
	}
	
	public boolean isMultiple() {
		return false;
	}

    
	/**
     * @param value the value to set
     */
    public void setValue(Encounter value) {
    	this.value = value;
    }

	
    public String getValueAsString() {
		if(value != null)
		{
			StringBuilder result = new StringBuilder();
			result.append(value.getEncounterType().getName());
			result.append(" Date: ");
			result.append(new SimpleDateFormat(dateFormat).format(value.getEncounterDatetime()));
			return result.toString();
		}
		return null;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
