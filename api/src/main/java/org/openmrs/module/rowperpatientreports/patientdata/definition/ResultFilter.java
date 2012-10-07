package org.openmrs.module.rowperpatientreports.patientdata.definition;


public interface ResultFilter {
	
	public Object filter(Object Result);

	public Object filterWhenNull();
}
