package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;

/**
 * This PatientData will return all unvoided drug orders for a patient. The 
 * DrugOrders returned will be filtered based on the drugs contained in the 
 * specified conceptSet (so the conceptSet should be a set of drug concepts).
 * 
 * @author Lara Kellett
 *
 */
public class AllDrugOrdersRestrictedByConceptSet extends BasePatientData implements RowPerPatientData {

	private Concept drugConceptSetConcept = null;

    /**
     * @return the drugConceptSet
     */
    public Concept getDrugConceptSetConcept() {
    	return drugConceptSetConcept;
    }

	
    /**
     * The conceptSet containing the drugs that the drug order list
     * should be filtered against 
     * 
     * @param drugConceptSet the drugConceptSet to set
     */
    public void setDrugConceptSetConcept(Concept drugConceptSetConcept) {
    	this.drugConceptSetConcept = drugConceptSetConcept;
    }
	
}
