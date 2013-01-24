package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;



public class DateOfWorkflowStateChange extends BasePatientData implements DateOfPatientData {

	private Concept concept;
	
	private String dateFormat = "yyyy-MM-dd";
	
	@ConfigurationProperty
	private Date endDate = null;
	
	@ConfigurationProperty
	private Date startDate = null;

	
    /**
     * @return the concept
     */
    public Concept getConcept() {
    	return concept;
    }

	
    /**
     * @param concept the concept to set
     */
    public void setConcept(Concept concept) {
    	this.concept = concept;
    }

    public void setDateFormat(String dateFormat) {
	    this.dateFormat = dateFormat;
    }


	public String getDateFormat() {
	    return dateFormat;
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
