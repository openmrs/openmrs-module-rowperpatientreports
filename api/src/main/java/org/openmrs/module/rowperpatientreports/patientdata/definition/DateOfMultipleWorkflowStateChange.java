package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;



public class DateOfMultipleWorkflowStateChange extends BasePatientData implements DateOfPatientData {

	private List<Concept> concepts;
	
	private String dateFormat = "yyyy-MM-dd";
	
	@ConfigurationProperty
	private Date startDate = null;
	
	@ConfigurationProperty
	private Date endDate = null;

    /**
     * @return the concepts
     */
    public List<Concept> getConcepts() {
    	return concepts;
    }

	
    /**
     * @param concepts the concepts to set
     */
    public void setConcepts(List<Concept> concepts) {
    	this.concepts = concepts;
    }


	public void setDateFormat(String dateFormat) {
	    this.dateFormat = dateFormat;
    }


	public String getDateFormat() {
	    return dateFormat;
    }

	
    public Date getStartDate() {
    	return startDate;
    }
	
    public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }

    public Date getEndDate() {
    	return endDate;
    }
    
    public void setEndDate(Date endDate) {
    	this.endDate = endDate;
    }
}
