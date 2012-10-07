package org.openmrs.module.rowperpatientreports.patientdata.definition;



public interface DateOfPatientData extends RowPerPatientData {
	
	public void setDateFormat(String dateFormat);
	
	public String getDateFormat();

}
