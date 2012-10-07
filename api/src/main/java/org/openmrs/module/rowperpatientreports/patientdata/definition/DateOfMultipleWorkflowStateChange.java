package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.List;

import org.openmrs.Concept;



public class DateOfMultipleWorkflowStateChange extends BasePatientData implements DateOfPatientData {

	private List<Concept> concepts;
	
	private String dateFormat = "yyyy-MM-dd";

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
}
