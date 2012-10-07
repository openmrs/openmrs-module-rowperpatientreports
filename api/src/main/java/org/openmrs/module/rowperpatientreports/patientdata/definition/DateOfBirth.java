package org.openmrs.module.rowperpatientreports.patientdata.definition;



public class DateOfBirth extends BasePatientData implements RowPerPatientData, DateOfPatientData {

	private String dateFormat = "yyyy-MM-dd";
	
	
	public DateOfBirth() {
		super();
		setName("DateOfBirth");
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
