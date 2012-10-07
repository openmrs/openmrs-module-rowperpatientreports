package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;

/**
 * This PatientData will return a list of all unvoided drug orders for the patients. 
 * The results will be filtered to only return drug orders for the specified 
 * concept (which should be linked to a drug).
 * 
 * @author Lara Kellett
 *
 */
public class AllDrugOrdersRestrictedByConcept extends BasePatientData implements RowPerPatientData {

	private Concept concept = null;

	
    /**
     * @return the concept
     */
    public Concept getConcept() {
    	return concept;
    }

    /**
     * The concept for the drug that the drug orders should 
     * be filtered against
     * 
     * @param concept
     */
    public void setConcept(Concept concept) {
    	this.concept = concept;
    }
	
}
