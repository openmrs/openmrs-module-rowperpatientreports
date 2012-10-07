/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.rowperpatientreports.dataset.definition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.definition.BaseDataSetDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;

/**
 * This is the simplest implementation of the row per patient reports
 * it takes in a series of filters (cohorts). Once all the cohorts are evaluated
 * for each of the patients the columns assigned to the dataset will be used
 * to evaluate the patient data to be displayed by the report
 */
@Localized("reporting.RowPerPatientDataSetDefinition")
public class RowPerPatientDataSetDefinition extends BaseDataSetDefinition {

	private static final long serialVersionUID = 6405583324151111487L;
	
	@ConfigurationProperty(group="properties")
	private List<Mapped<RowPerPatientData>> columns;
	
	@ConfigurationProperty(group="properties")
	private List<Mapped<CohortDefinition>> filters;
	
	@ConfigurationProperty(group="properties")
	private SortCriteria sortCriteria = null;
	
	/**
	 * Constructor
	 */
	public RowPerPatientDataSetDefinition() {
		super();
	}
	
	/**
	 * Public constructor with name and description
	 */
	public RowPerPatientDataSetDefinition(String name, String description) {
		super(name, description);
	}

	
    /**
     * @return the columns
     */
    public List<Mapped<RowPerPatientData>> getColumns() {
    	if (columns == null) {
			columns = new ArrayList<Mapped<RowPerPatientData>>();
		}
    	return columns;
    }

	
    /**
     * @param columns the columns to set
     */
    public void setColumns(List<Mapped<RowPerPatientData>> columns) {
    	this.columns = columns;
    }


    /**
     * @param column the patient data column to be added
     * @param mappings the parameter mappings that should be used with the column
     */
	public void addColumn(RowPerPatientData column, Map<String, Object> mappings)
    {
    	getColumns().add(new Mapped<RowPerPatientData>(column, mappings));
    }
	
    /**
     * @return the filters
     */
    public List<Mapped<CohortDefinition>> getFilters() {
    	if (filters == null) {
			filters = new ArrayList<Mapped<CohortDefinition>>();
		}
    	return filters;
    }

	
    /**
     * @param filters the filters to set
     */
    public void setFilters(List<Mapped<CohortDefinition>> filters) {
    	this.filters = filters;
    }

    /**
     * @param cohort the cohort to be added
     * @param mappings the parameter mappings that should be used with the cohort
     */
    public void addFilter(CohortDefinition cohort, Map<String, Object> mappings)
    {
    	getFilters().add(new Mapped<CohortDefinition>(cohort, mappings));
    }
	
    public SortCriteria getSortCriteria() {
    	return sortCriteria;
    }

    public void setSortCriteria(SortCriteria sortCriteria) {
    	this.sortCriteria = sortCriteria;
    }
}

