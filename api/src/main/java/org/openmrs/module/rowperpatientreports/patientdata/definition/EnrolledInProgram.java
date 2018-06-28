package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Program;

import java.text.SimpleDateFormat;


public class EnrolledInProgram extends BasePatientData implements DateOfPatientData {

	private Program program;

	private ResultFilter filter;

	private String valueType;

	private String dateFormat;


	@Override
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public ResultFilter getFilter() {
		return filter;
	}

	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}


}
