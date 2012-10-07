package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;
import org.openmrs.EncounterType;

/**
 * This PatientData will return a list of all unvoided drug orders for the patients. 
 * The results will be filtered to only return drug orders for the specified 
 * concept (which should be linked to a drug).
 * 
 * @author Lara Kellett
 *
 */
public class ObservationPresentInMostRecentOrder extends BasePatientData implements RowPerPatientData {

	private Concept observationConcept = null;
	
	private Concept OrderConcept = null;
	
	private EncounterType encounterType = null;
	
	private ResultFilter filter = null;

	public Concept getObservationConcept() {
		return observationConcept;
	}

	public void setObservationConcept(Concept observationConcept) {
		this.observationConcept = observationConcept;
	}
	
	public Concept getOrderConcept() {
		return OrderConcept;
	}

	public void setOrderConcept(Concept orderConcept) {
		OrderConcept = orderConcept;
	}

	public EncounterType getEncounterType() {
		return encounterType;
	}

	public void setEncounterType(EncounterType encounterType) {
		this.encounterType = encounterType;
	}

	public ResultFilter getFilter() {
		return filter;
	}

	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}	
}
