package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;



public class AllDrugOrdersRestrictedByMultipleConcepts extends BasePatientData implements RowPerPatientData {

	private List<Concept> concepts = null;

	
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
    
    public void addConcept(Concept concept)
    {
    	getConcepts().add(concept);
    }
}
