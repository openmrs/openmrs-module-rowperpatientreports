package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.EncounterType;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;




public class DateOfMostRecentEncounterOfType extends BasePatientData implements DateOfPatientData {

	private List<EncounterType> encounterTypes = new ArrayList<EncounterType>();
	
	private String dateFormat = "yyyy-MM-dd";
	
	@ConfigurationProperty
	private Date endDate = null;
	
	@ConfigurationProperty
	private Date startDate = null;


    public void setDateFormat(String dateFormat) {
	    this.dateFormat = dateFormat;
    }


	public String getDateFormat() {
	    return dateFormat;
    }

    public List<EncounterType> getEncounterTypes() {
		return encounterTypes;
	}

	public void setEncounterTypes(List<EncounterType> encounterTypes) {
		this.encounterTypes = encounterTypes;
		
	}
	
	public void addEncounterType(EncounterType encounterType)
	{
		encounterTypes.add(encounterType);
	}
	
    public Date getEndDate() {
    	return endDate;
    }
	
    public void setEndDate(Date endDate) {
    	this.endDate = endDate;
    }
	
    public Date getStartDate() {
    	return startDate;
    }

    public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }
}
