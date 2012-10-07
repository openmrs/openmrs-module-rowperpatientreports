package org.openmrs.module.rowperpatientreports.patientdata.result;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;

public class DateOfWorkflowStateChangeResult extends BasePatientDataResult {

	private Date value;

	private String dateFormat = "yyyy-MM-dd";

	public DateOfWorkflowStateChangeResult(RowPerPatientData patientData,
			EvaluationContext ec) {
		super(patientData, ec);
	}

	public Class<?> getColumnClass() {
		return Date.class;
	}

	public Date getValue() {
		return value;
	}

	public boolean isMultiple() {
		return false;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Date value) {
		this.value = value;
	}

	public String getValueAsString() {
		
		return new SimpleDateFormat(dateFormat).format(value);
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
