package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;



public class MostRecentObservation extends BasePatientData implements RowPerPatientData {

	private Concept concept;
	
	private ResultFilter filter = null;
	
	private boolean includeNull = true;
	
    /**
     * @return the concept
     */
    public Concept getConcept() {
    	return concept;
    }

	
    /**
     * @param concept the concept to set
     */
    public void setConcept(Concept concept) {
    	this.concept = concept;
    	if(concept != null)
    	{
    		setName(concept.getName().getName());
    		setDescription(concept.getDisplayString());
    	}
    }


	public ResultFilter getFilter() {
		return filter;
	}


	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}


	public boolean isIncludeNull() {
		return includeNull;
	}


	public void setIncludeNull(boolean includeNull) {
		this.includeNull = includeNull;
	}
	
	
}
