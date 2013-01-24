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
package org.openmrs.module.rowperpatientreports.dataset.definition.evaluator;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.common.ReflectionUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.evaluator.DataSetEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.rowperpatientreports.dataset.definition.RowPerPatientDataSetDefinition;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.AllDrugOrdersResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.DrugOrdersResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.EncounterResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.EvaluateForOtherPatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.NumberResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

/**
 * The logic that evaluates a {@link RowPerPatientDataSetDefinition} and produces an {@link DataSet}
 * @see RowPerPatientDataSetDefinition
 */
@Handler(supports={RowPerPatientDataSetDefinition.class})
public class RowPerPatientDataSetEvaluator implements DataSetEvaluator {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * Public constructor
	 */
	public RowPerPatientDataSetEvaluator() { }
	
	/**
	 * @see DataSetEvaluator#evaluate(DataSetDefinition, EvaluationContext)
	 * @should evaluate a PatientDataSetDefinition
	 */
	public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext context) throws EvaluationException{
		

		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		
		RowPerPatientDataSetDefinition definition = (RowPerPatientDataSetDefinition) dataSetDefinition;
		dataSet.setSortCriteria(definition.getSortCriteria());
		
		context = ObjectUtil.nvl(context, new EvaluationContext());
		//evaluate the base cohort
		Cohort cohort = context.getBaseCohort();

		// By default, get all patients
		if (cohort == null) {
			cohort = Context.getPatientSetService().getAllPatients();
		}
		
		//for all filters assigned to the dataset evaluate then intersect to get
		//a resulting set of patients that are a part of all the cohorts
		for(Mapped<CohortDefinition> cd: definition.getFilters())
		{
			Cohort filter;
			try {
				filter = Context.getService(CohortDefinitionService.class).evaluate(cd, context);
				cohort = Cohort.intersect(cohort, filter);
			} catch (EvaluationException e) {
				log.error("Unable to evaluate Filter", e);
			}
		}
		
		//left here for testing so that you can restrict the number of patients returned to 10
		//to speed up execution time
		int i = 0;
		for (Integer patientId : cohort.getMemberIds()) {			
			DataSetRow row = new DataSetRow();
			
			//left here for testing purposes
			i++;
			if(i > 10)
			{
				break;
			}
					
			for(Mapped<RowPerPatientData> mapped: definition.getColumns())
			{
				//set up patientData and evaluate
				RowPerPatientData pd = mapped.getParameterizable();
				pd.setPatientId(patientId);
				pd.setPatient(Context.getPatientService().getPatient(patientId));
			
				EvaluationContext childContext = EvaluationContext.cloneForChild(context, mapped);
				for (Parameter p : pd.getParameters()) {
					Object value = p.getDefaultValue();
					if (context != null && childContext.containsParameter(p.getName())) {
						value = childContext.getParameterValue(p.getName());
					}
					ReflectionUtil.setPropertyValue(pd, p.getName(), value);
				}
				mapped.setParameterizable(pd);
				
				
				PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(mapped, childContext);
				
				addDataToRow(patientDataResult, row);
			}
			dataSet.addRow(row);
		}
		
		return dataSet;
	}
	
	private void addDataToRow(PatientDataResult patientDataResult, DataSetRow row)
	{
		if(patientDataResult instanceof EvaluateForOtherPatientDataResult)
		{
			List<PatientDataResult> results = (List<PatientDataResult>)patientDataResult.getValue();
			for(PatientDataResult pdr: results)
			{
				addDataToRow(pdr, row);
			}
		}
		else if(patientDataResult instanceof AllDrugOrdersResult)
		{
			AllDrugOrdersResult res = (AllDrugOrdersResult)patientDataResult;
			StringBuilder drugs = new StringBuilder();
			StringBuilder startDate = new StringBuilder();
			StringBuilder endDate = new StringBuilder();
			int drug = 0;
			for(DrugOrder drO: res.getValue())
		    {
	    		try{
	    			if(drug > 0)
	    			{
	    				drugs.append(" + ");
	    			}
	    			
	    			if(res.getDrugFilter() != null)
	    			{
	    				drugs.append(res.getDrugFilter().filter(drO));
	    			}
	    			else
	    			{
	    				drugs.append(drO.getDrug().getName());
	    			}
	    			
	    			if(drug == 0)
	    			{
		    			startDate.append(new SimpleDateFormat(res.getDateFormat()).format(drO.getStartDate()));
		    			
		    			if(drO.getDiscontinuedDate() != null)
		    			{
		    				endDate.append(new SimpleDateFormat(res.getDateFormat()).format(drO.getDiscontinuedDate()));
		    			}
	    			}
	    		}
	    		catch(Exception e)
	    		{
	    			log.info("Error retrieving drug info", e);
	    		} 	
	    		drug ++;
		    }
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), String.class);
			row.addColumnValue(c, patientDataResult.getValueAsString());
			DataSetColumn c1 = new DataSetColumn(patientDataResult.getName() + " Drugs", patientDataResult.getDescription() + " Drugs", String.class);
			row.addColumnValue(c1, drugs.toString().trim());
			DataSetColumn c2 = new DataSetColumn(patientDataResult.getName() + " StartDate", patientDataResult.getDescription() + " StartDate", String.class);
			row.addColumnValue(c2, startDate.toString().trim());
			DataSetColumn c3 = new DataSetColumn(patientDataResult.getName() + " EndDate", patientDataResult.getDescription() + " EndDate", String.class);
			row.addColumnValue(c3, endDate.toString().trim());
		}
		else if(patientDataResult instanceof DrugOrdersResult)
		{
			DrugOrdersResult res = (DrugOrdersResult)patientDataResult;
			String drugs = ""; 
			String startDate = ""; 
			String endDate = ""; 
			DrugOrder dr = res.getValue();
			
			if(dr != null)
			{
	    		drugs = dr.getDrug().getName();
	    			
		    	startDate = new SimpleDateFormat(res.getDateFormat()).format(dr.getStartDate());
		    			
		    	if(dr.getDiscontinuedDate() != null)
    			{
    				endDate = new SimpleDateFormat(res.getDateFormat()).format(dr.getDiscontinuedDate());
    			}
		    }
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), String.class);
			row.addColumnValue(c, patientDataResult.getValueAsString());
			DataSetColumn c1 = new DataSetColumn(patientDataResult.getName() + " Drugs", patientDataResult.getDescription() + " Drugs", String.class);
			row.addColumnValue(c1, drugs);
			DataSetColumn c2 = new DataSetColumn(patientDataResult.getName() + " StartDate", patientDataResult.getDescription() + " StartDate", String.class);
			row.addColumnValue(c2, startDate);
			DataSetColumn c3 = new DataSetColumn(patientDataResult.getName() + " EndDate", patientDataResult.getDescription() + " EndDate", String.class);
			row.addColumnValue(c3, endDate);
		}
		//set up SimpleDataSet column value for evaluated patientData
		else if(patientDataResult.isMultiple())
		{
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), String.class);
			row.addColumnValue(c, patientDataResult.getValueAsString());
		}
		//if it is an observation result we are also going to include the date as a column, so that the 
		//values don't need to be retrieved twice
		else if(patientDataResult instanceof ObservationResult)
		{
			ObservationResult obsRes = (ObservationResult)patientDataResult;
			
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), patientDataResult.getColumnClass());
			row.addColumnValue(c, patientDataResult.getValue());
			
			DataSetColumn d = new DataSetColumn(patientDataResult.getName() + " Date", patientDataResult.getDescription() + " Date", String.class);
			if(obsRes.getDateOfObservation() != null)
			{
				row.addColumnValue(d, new SimpleDateFormat(obsRes.getDateFormat()).format(obsRes.getDateOfObservation()));
			}
			else
			{
				row.addColumnValue(d, null);
			}
		}
		else if(patientDataResult instanceof DateValueResult)
		{
			DateValueResult obsRes = (DateValueResult)patientDataResult;
			
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), patientDataResult.getColumnClass());
			row.addColumnValue(c, patientDataResult.getValue());
			
			DataSetColumn d = new DataSetColumn(patientDataResult.getName() + " Date", patientDataResult.getDescription() + " Date", String.class);
			if(obsRes.getDateOfObservation() != null)
			{
				row.addColumnValue(d, new SimpleDateFormat(obsRes.getDateFormat()).format(obsRes.getDateOfObservation()));
			}
			else
			{
				row.addColumnValue(d, null);
			}
		}
		else if(patientDataResult instanceof EncounterResult)
		{
			EncounterResult encRes = (EncounterResult)patientDataResult;
			
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), patientDataResult.getColumnClass());
			row.addColumnValue(c, patientDataResult.getValue());
			
			DataSetColumn d = new DataSetColumn(patientDataResult.getName() + " Date", patientDataResult.getDescription() + " Date", String.class);
			if(encRes.getValue() != null)
			{
				Encounter e = encRes.getValue();
				row.addColumnValue(d, new SimpleDateFormat(encRes.getDateFormat()).format(e.getEncounterDatetime()));
			}
			else
			{
				row.addColumnValue(d, null);
			}
		}
		else if(patientDataResult instanceof NumberResult)
		{
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), Number.class);
			row.addColumnValue(c, patientDataResult.getValueAsString());
		}
		else
		{
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), String.class);
			row.addColumnValue(c, patientDataResult.getValueAsString());
		}
	}
}
