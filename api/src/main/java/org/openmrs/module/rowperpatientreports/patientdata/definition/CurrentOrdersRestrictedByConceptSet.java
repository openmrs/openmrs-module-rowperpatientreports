package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Calendar;
import java.util.Date;

import org.openmrs.Concept;



public class CurrentOrdersRestrictedByConceptSet extends BasePatientData implements RowPerPatientData {

	private Concept drugConceptSetConcept = null;

	private Date onDate = Calendar.getInstance().getTime();
	
	private ResultFilter drugFilter = null;
	
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
     * @return the onDate
     */
    public Date getOnDate() {
    	return onDate;
    }


	
    /**
     * @param onDate the onDate to set
     */
    public void setOnDate(Date onDate) {
    	this.onDate = onDate;
    }


	public ResultFilter getDrugFilter() {
		return drugFilter;
	}


	public void setDrugFilter(ResultFilter drugFilter) {
		this.drugFilter = drugFilter;
	}
    
}
