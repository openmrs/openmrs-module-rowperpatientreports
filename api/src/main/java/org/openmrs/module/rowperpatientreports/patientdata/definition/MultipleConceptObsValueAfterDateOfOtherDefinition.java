package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;



public class MultipleConceptObsValueAfterDateOfOtherDefinition extends BasePatientData implements RowPerPatientData {
	
	private Mapped<RowPerPatientData> dateOfPatientData;

	
	private List<Concept> concepts;
	
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


	
    /**
     * @return the dateOfPatientData
     */
    public Mapped<RowPerPatientData> getDateOfPatientData() {
    	return dateOfPatientData;
    }


	
    /**
     * @param dateOfPatientData the dateOfPatientData to set
     */
    public void setDateOfPatientData(DateOfPatientData dateOfPatientData, Map<String, Object> mappings) {
    	this.dateOfPatientData = new Mapped<RowPerPatientData>(dateOfPatientData, mappings);
    }
}
