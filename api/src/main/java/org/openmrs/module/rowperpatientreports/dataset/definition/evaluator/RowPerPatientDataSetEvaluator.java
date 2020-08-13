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

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.DrugOrder;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.CohortUtil;
import org.openmrs.module.reporting.cohort.Cohorts;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.common.DateUtil;
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
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.module.reporting.report.service.ReportServiceImpl;
import org.openmrs.module.rowperpatientreports.dataset.definition.RowPerPatientDataSetDefinition;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.AllDrugOrdersResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.AllObservationValuesResult;
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
		
		//for all filters assigned to the dataset evaluate then intersect to get
		//a resulting set of patients that are a part of all the cohorts
		for(Mapped<CohortDefinition> cd: definition.getFilters())
		{
			Cohort filter;
			try {
				filter = Context.getService(CohortDefinitionService.class).evaluate(cd, context);
				if (cohort == null) {
					cohort = filter;
				}
				else {
					cohort = CohortUtil.intersect(cohort, filter);
				}
			}
			catch (EvaluationException e) {
				log.error("Unable to evaluate Filter", e);
			}
		}

		// By default, get all patients
		if (cohort == null) {
			cohort = Cohorts.allPatients();
		}

		String msg = "Evaluating Row Per Patient Data: ";
		logMessage(msg + cohort.size() + " rows and " + definition.getColumns().size() + " columns", context);
        StopWatch sw = new StopWatch();
        sw.start();

		int i = 0;
		int numSecondsBetweenMessages = 15;
		int numMessagesLogged = 0;

		for (Integer patientId : cohort.getMemberIds()) {			
			DataSetRow row = new DataSetRow();

			i++;

			long currentDuration = sw.getTime();
			int numSecondsElapsed = (int)(currentDuration/1000);

			if(numSecondsElapsed/numSecondsBetweenMessages > numMessagesLogged) {
			    logMessage(msg + i + " / " + cohort.size() + " ( " + (int)(i*100/cohort.size()) + " % )", context);
			    numMessagesLogged++;
			}

            Context.clearSession();
					
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

		sw.stop();
		logMessage(msg + " Complete in " + sw.toString(), context);
		
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
			Date startDate = null;
			Date endDate = null;
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
		    			startDate = drO.getEffectiveStartDate();

		    			if(drO.getEffectiveStopDate() != null)
		    			{
		    				endDate = drO.getEffectiveStopDate();
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

            addDateColumn(row, res.getName() + " StartDate", res.getDescription() + " StartDate", startDate, res.getDateFormat());
            addDateColumn(row, res.getName() + " EndDate", res.getDescription() + " EndDate", endDate, res.getDateFormat());
		}
		else if(patientDataResult instanceof DrugOrdersResult)
		{
            DrugOrdersResult res = (DrugOrdersResult)patientDataResult;
			String drugs = ""; 
			Date startDate = null;
			Date endDate = null;
			DrugOrder dr = res.getValue();
			
			if(dr != null)
			{
	    		drugs = dr.getDrug().getName();

		    	startDate = dr.getEffectiveStartDate();

		    	if(dr.getEffectiveStopDate() != null)
    			{
    				endDate = dr.getEffectiveStopDate();
    			}
		    }
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), String.class);
			row.addColumnValue(c, patientDataResult.getValueAsString());
			DataSetColumn c1 = new DataSetColumn(patientDataResult.getName() + " Drugs", patientDataResult.getDescription() + " Drugs", String.class);
			row.addColumnValue(c1, drugs);

            addDateColumn(row, res.getName() + " StartDate", res.getDescription() + " StartDate", startDate, res.getDateFormat());
            addDateColumn(row, res.getName() + " EndDate", res.getDescription() + " EndDate", endDate, res.getDateFormat());
		}
		//if it is an observation result we are also going to include the date as a column, so that the
		//values don't need to be retrieved twice
		else if(patientDataResult instanceof ObservationResult)
		{
            DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), patientDataResult.getColumnClass());
            row.addColumnValue(c, patientDataResult.getValue());

			ObservationResult obsRes = (ObservationResult)patientDataResult;
            addDateColumn(row, obsRes.getName() + " Date", obsRes.getDescription() + " Date", obsRes.getDateOfObservation(), obsRes.getDateFormat());
		}
		else if(patientDataResult instanceof AllObservationValuesResult)
		{
			DataSetColumn cc = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), String.class);
			row.addColumnValue(cc, patientDataResult.getValueAsString());

			AllObservationValuesResult obsRes = (AllObservationValuesResult)patientDataResult;

			if(obsRes.getValue() != null)
			{
				int i = 1;
				for(Obs o: obsRes.getValue())
				{
					DataSetColumn c = new DataSetColumn(patientDataResult.getName() + i, patientDataResult.getDescription(), patientDataResult.getColumnClass());
					row.addColumnValue(c, o.getValueAsString(Context.getLocale()));

                    addDateColumn(row, obsRes.getName() + i + " Date", obsRes.getDescription() + i + " Date", o.getObsDatetime(), obsRes.getDateFormat());
					i++;
				}
				
				if(i < obsRes.getMinResultsOutput())
				{
					for(int j = i; j <= obsRes.getMinResultsOutput(); j++)
					{
						DataSetColumn c = new DataSetColumn(patientDataResult.getName() + j, patientDataResult.getDescription(), patientDataResult.getColumnClass());
						row.addColumnValue(c, null);
						
						DataSetColumn d = new DataSetColumn(patientDataResult.getName() + j + " Date", patientDataResult.getDescription() + " Date", String.class);
						row.addColumnValue(d, null);
					}
				}
			}
		}
		//set up SimpleDataSet column value for evaluated patientData
		else if(patientDataResult.isMultiple())
		{
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), String.class);
			row.addColumnValue(c, patientDataResult.getValueAsString());
		}
		else if(patientDataResult instanceof DateValueResult)
		{
			DateValueResult obsRes = (DateValueResult)patientDataResult;
			
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), patientDataResult.getColumnClass());
			row.addColumnValue(c, patientDataResult.getValue());

            addDateColumn(row, obsRes.getName() + " Date", obsRes.getDescription() + " Date", obsRes.getDateOfObservation(), obsRes.getDateFormat());
		}
		else if(patientDataResult instanceof EncounterResult)
		{
			EncounterResult encRes = (EncounterResult)patientDataResult;
			
			DataSetColumn c = new DataSetColumn(patientDataResult.getName(), patientDataResult.getDescription(), patientDataResult.getColumnClass());
			row.addColumnValue(c, patientDataResult.getValue());

            Date dateVal = encRes.getValue() != null ? encRes.getValue().getEncounterDatetime() : null;
            addDateColumn(row, encRes.getName() + " Date", encRes.getDescription() + " Date", dateVal, encRes.getDateFormat());
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

    private void addDateColumn(DataSetRow row, String columnName, String columnDescription, Date dateValue, String format) {
        Class dataType = format == null ? Date.class : String.class;
        Object value = dateValue;
        if (format != null) {
            value = (dateValue == null ? "" : DateUtil.formatDate(dateValue, format));
        }
        DataSetColumn column = new DataSetColumn(columnName, columnDescription + " StartDate", dataType);
        row.addColumnValue(column, value);
    }

    private void logMessage(String message, EvaluationContext context) {
        ReportService rs = Context.getService(ReportService.class);
        if (context != null) {
            String requestUuid = (String)context.getContextValues().get(ReportServiceImpl.REPORT_REQUEST_UUID);
            if (ObjectUtil.notNull(requestUuid)) {
                rs.logReportMessage(requestUuid, message);
            }
        }
    }
}
