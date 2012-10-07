package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;



public class AllObservationValues extends BasePatientData implements RowPerPatientData {

	private Concept concept;
	
	private ResultFilter filter;
	
	private ResultFilter outputFilter;

	
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


	public ResultFilter getOutputFilter() {
		return outputFilter;
	}


	public void setOutputFilter(ResultFilter outputFilter) {
		this.outputFilter = outputFilter;
	}
}
