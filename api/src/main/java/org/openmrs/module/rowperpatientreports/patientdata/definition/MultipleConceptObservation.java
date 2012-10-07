package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;



public class MultipleConceptObservation extends BasePatientData implements RowPerPatientData {

	private List<Concept> concepts;
	
	private ResultFilter filter;
	
    /**
     * @return the concept
     */
    public List<Concept> getConcepts() {
    	if(concepts == null)
    	{
    		concepts = new ArrayList<Concept>();
    	}
    	return concepts;
    }

	
    /**
     * @param concept the concept to set
     */
    public void setConcepts(List<Concept> concepts) {
    	this.concepts = concepts;
    }

    public void addConcept(Concept concept)
    {
    	getConcepts().add(concept);
    }


	public ResultFilter getFilter() {
		return filter;
	}


	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}
}
