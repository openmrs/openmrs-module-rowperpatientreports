package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;



public class FirstDrugOrderStartedRestrictedByConceptSet extends BasePatientData implements RowPerPatientData {

	private Concept drugConceptSetConcept = null;
	
	@ConfigurationProperty
	private Date startDate = null;
	
	@ConfigurationProperty
	private Date endDate = null;
	
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


	
    public Date getStartDate() {
    	return startDate;
    }


	
    public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }


	
    public Date getEndDate() {
    	return endDate;
    }


	
    public void setEndDate(Date endDate) {
    	this.endDate = endDate;
    }
    
}
