package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

public class DateDiff extends BasePatientData implements RowPerPatientData {

	private List<EncounterType> encounterTypes=null;
	private Concept concept;
	public enum DateDiffType {
		/**
		 * Indicates a date difference in days
		 */
		DAYS,
		/**
		 * Indicates a date difference in hours
		 */
		HOURS,
		/**
		 * Indicates a date difference in minutes
		 */
		MINUTES,
		/**
		 * Indicates a date difference in months
		 */
		MONTHS,
		/**
		 * Indicates a date difference in seconds
		 */
		SECONDS,/**
		 * Indicates a date difference in weeks
		 */
		WEEKS,
		/**
		 * Indicates a date difference in years
		 */
		YEARS;
		
	}
	private DateDiffType dateDiffType;
	@ConfigurationProperty(group="endDate")
	private Date endDate = new Date();
	public Concept getConcept() {
		return concept;
	}
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<EncounterType> getEncounterTypes() {
		return encounterTypes;
	}
	public void setEncounterTypes(List<EncounterType> encounterTypes) {
		this.encounterTypes = encounterTypes;
	}
	public void setDateDiffType(DateDiffType dateDiffType) {
		this.dateDiffType = dateDiffType;
	}
	public DateDiffType getDateDiffType() {
		return dateDiffType;
	}	
	
}
