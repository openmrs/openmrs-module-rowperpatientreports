package org.openmrs.module.rowperpatientreports.patientdata.definition;



public class DateOfBirthShowingEstimation extends BasePatientData implements RowPerPatientData {

	private String dateFormat = "yyyy-MM-dd";
	private String estimatedDateFormat = "yyyy-MM-dd";
	
	public DateOfBirthShowingEstimation() {
		super();
		setName("DOB");
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getEstimatedDateFormat() {
		return estimatedDateFormat;
	}
	public void setEstimatedDateFormat(String estimatedDateFormat) {
		this.estimatedDateFormat = estimatedDateFormat;
	}
}
