package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Calendar;



public class DateOfNextTestDueFromBirth extends BasePatientData implements RowPerPatientData, DateOfPatientData {

	private String dateFormat = "yyyy-MM-dd";
	
	private int timeUnit = Calendar.DAY_OF_YEAR;
	private int timeIncrement = 0;
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public int getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(int timeUnit) {
		this.timeUnit = timeUnit;
	}
	public int getTimeIncrement() {
		return timeIncrement;
	}
	public void setTimeIncrement(int timeIncrement) {
		this.timeIncrement = timeIncrement;
	}

}
