package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;



public class AllDrugOrdersRestrictedByMultipleConceptSet extends BasePatientData implements RowPerPatientData {

	private List<Concept> conceptSets = null;

	
    /**
     * @return the concepts
     */
    public List<Concept> getConceptSets() {
    	if(conceptSets == null)
    	{
    		conceptSets = new ArrayList<Concept>();
    	}
    	return conceptSets;
    }

	
    /**
     * @param concepts the concepts to set
     */
    public void setConceptSets(List<Concept> conceptSets) {
    	this.conceptSets = conceptSets;
    }
    
    public void addConceptSet(Concept conceptSet)
    {
    	getConceptSets().add(conceptSet);
    }
}
