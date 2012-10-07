package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;



public class DateOfMostRecentObservationHavingCodedAnswer extends BasePatientData implements DateOfPatientData {

	private List<Concept> concepts;
	
	private String dateFormat = "yyyy-MM-dd";
	
	private List<Concept> answers;

    /**
     * @return the concepts
     */
    public List<Concept> getConcepts() {
    	if(concepts == null)
    	{
    		concepts = new ArrayList<Concept>();
    	}
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
	
	public void addConcept(Concept concept)
	{
		getConcepts().add(concept);
	}


	public List<Concept> getAnswers() {
		if(answers == null)
		{
			answers = new ArrayList<Concept>();
		}
		return answers;
	}


	public void setAnswers(List<Concept> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(Concept answer)
	{
		getAnswers().add(answer);
	}
	
}
