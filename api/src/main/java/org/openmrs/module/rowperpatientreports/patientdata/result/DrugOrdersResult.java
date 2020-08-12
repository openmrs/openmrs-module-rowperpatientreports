package org.openmrs.module.rowperpatientreports.patientdata.result;

import org.openmrs.DrugOrder;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class DrugOrdersResult extends BasePatientDataResult {
	
	private DrugOrder value;
	
	private String dateFormat = "yyyy-MM-dd";
	
	public DrugOrdersResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
		return String.class;
	}
	
	public DrugOrder getValue() {
		return value;
	}
	
	public boolean isMultiple() {
		return true;
	}

    
	/**
     * @param value the value to set
     */
    public void setValue(DrugOrder value) {
    	this.value = value;
    }

	
    public String getValueAsString() {
		if(value != null)
		{
			StringBuilder result = new StringBuilder();
		    
			result.append(value.getDrug().getName());
			result.append(" StartDate: ");
			result.append(value.getEffectiveStartDate());
			result.append(" EndDate: ");
			result.append(value.getEffectiveStopDate());
			return result.toString();
		}
		return null;
	}
    
    public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		if(dateFormat != null)
		{	
			this.dateFormat = dateFormat;
		}
	}
}
