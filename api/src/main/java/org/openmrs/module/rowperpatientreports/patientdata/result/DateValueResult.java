package org.openmrs.module.rowperpatientreports.patientdata.result;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.ResultFilter;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class DateValueResult extends BasePatientDataResult {
	
	private Date dateOfObservation;
	private String dateFormat = "yyyy/MM/dd";
	private String value;
	
	public DateValueResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
	    if(patientData != null)
	    {
	    	setDateFormat(patientData.getDateFormat());
	    }
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
	
	public boolean isMultiple() {
		return false;
	}

	/**
     * @return the dateOfObservation
     */
    public Date getDateOfObservation() {
    	return dateOfObservation;
    }

	
    /**
     * @param dateOfObservation the dateOfObservation to set
     */
    public void setDateOfObservation(Date dateOfObservation) {
    	this.dateOfObservation = dateOfObservation;
    }

    public String getValueAsString() {
		
	    return getValue() + " " + new SimpleDateFormat(dateFormat).format(dateOfObservation);
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
