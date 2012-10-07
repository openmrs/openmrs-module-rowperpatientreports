package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;



public class FirstDrugOrderStartedAfterDateRestrictedByConceptSet extends BasePatientData implements RowPerPatientData {

	private Concept drugConceptSetConcept = null;
	
	private Mapped<RowPerPatientData> dateOfPatientData;

	
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


	
    /**
     * @return the dateOfPatientData
     */
    public Mapped<RowPerPatientData> getDateOfPatientData() {
    	return dateOfPatientData;
    }


	
    /**
     * @param dateOfPatientData the dateOfPatientData to set
     */
    public void setDateOfPatientData(DateOfPatientData dateOfPatientData,  Map<String, Object> mappings) {
    	this.dateOfPatientData = new Mapped<RowPerPatientData>(dateOfPatientData, mappings);
    }
}
