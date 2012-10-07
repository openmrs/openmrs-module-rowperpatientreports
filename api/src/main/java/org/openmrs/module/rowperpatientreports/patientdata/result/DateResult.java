package org.openmrs.module.rowperpatientreports.patientdata.result;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class DateResult extends BasePatientDataResult {
	
	private Date value;
	
	private String format = "yyyy-MM-dd";
	
	public DateResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
		return Date.class;
	}
	
	public Date getValue() {
		return value;
	}
	
	public boolean isMultiple() {
		return false;
	}

    /**
     * @param value the value to set
     */
    public void setValue(Date value) {
    	this.value = value;
    }

	
    /**
     * @return the format
     */
    public String getFormat() {
    	return format;
    }

	
    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
    	this.format = format;
    }

	
    public String getValueAsString() {
	    if(getValue() != null)
	    {
	    	return new SimpleDateFormat(format).format(getValue());
	    }
	    
	    return null;
    }
}
