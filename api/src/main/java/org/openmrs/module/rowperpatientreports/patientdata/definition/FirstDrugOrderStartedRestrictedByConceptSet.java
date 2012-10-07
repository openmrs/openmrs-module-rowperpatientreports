package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;



public class FirstDrugOrderStartedRestrictedByConceptSet extends BasePatientData implements RowPerPatientData {

	private Concept drugConceptSetConcept = null;
	
    /**
     * @return the drugConceptSet
     */
    public Concept getDrugConceptSetConcept() {
    	return drugConceptSetConcept;
    }

	
    /**
     * @param drugConceptSet the drugConceptSet to set
     */
    public void setDrugConceptSetConcept(Concept drugConceptSetConcept) {
    	this.drugConceptSetConcept = drugConceptSetConcept;
    }
	
}
