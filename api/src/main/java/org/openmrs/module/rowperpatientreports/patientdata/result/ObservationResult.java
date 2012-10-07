package org.openmrs.module.rowperpatientreports.patientdata.result;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.ResultFilter;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class ObservationResult extends BasePatientDataResult {
	
	private Date dateOfObservation;
	private String dateFormat = "yyyy/MM/dd";
	private Obs obs;
	private ResultFilter resultFilter = null;
	
	public ObservationResult(RowPerPatientData patientData, EvaluationContext ec) {
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
		
		if(obs != null)
		{
			String resultValue = obs.getValueAsString(Context.getLocale());
	    	if(resultFilter != null)
	    	{
	    		return (String)resultFilter.filter(resultValue);
	    	}
	    	
			if(obs.getValueDatetime() != null)
			{
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				return sdf.format(obs.getValueDatetime());
			}
			return obs.getValueAsString(Context.getLocale());
		}
		
		return null;
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

	
    public Obs getObs() {
    	return obs;
    }

	
    public void setObs(Obs obs) {
    	this.obs = obs;
    }

	
    public ResultFilter getResultFilter() {
    	return resultFilter;
    }

	
    public void setResultFilter(ResultFilter resultFilter) {
    	this.resultFilter = resultFilter;
    }
}
