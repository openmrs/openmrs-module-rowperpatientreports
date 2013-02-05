package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.BaselineObservation;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={BaselineObservation.class})
public class BaselineObservationEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
	    
		ObservationResult par = new ObservationResult(patientData, context);
		
		BaselineObservation pd = (BaselineObservation)patientData;
		par.setDateFormat(pd.getDateFormat());
	
		Mapped<RowPerPatientData> mapped = pd.getDateOfPatientData();
		DateOfPatientData definition = (DateOfPatientData)mapped.getParameterizable();
		definition.setPatientId(pd.getPatientId());
		definition.setPatient(pd.getPatient());
		mapped.setParameterizable(definition);
		
		PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(mapped, context);
		
		Date dateOfObs = (Date)patientDataResult.getValue();
		
		if(dateOfObs != null)
		{
			if(pd.getOffset() > 0)
			{
				Calendar adjusted = Calendar.getInstance();
				adjusted.setTime(dateOfObs);
				adjusted.add(pd.getOffsetType(), pd.getOffset());
				
				dateOfObs = adjusted.getTime();
			}
			
			Calendar beforeDate = Calendar.getInstance();
			beforeDate.setTime(dateOfObs);
			beforeDate.add(Calendar.DAY_OF_YEAR, -pd.getBefore());
			
			if(pd.getStartDate() != null && pd.getStartDate().after(beforeDate.getTime()))
			{
				beforeDate.setTime(pd.getStartDate());
			}
			
			Calendar afterDate = Calendar.getInstance();
			afterDate.setTime(dateOfObs);
			afterDate.add(Calendar.DAY_OF_YEAR, pd.getAfter());
			
			if(pd.getEndDate() != null && pd.getEndDate().before(afterDate.getTime()))
			{
				afterDate.setTime(pd.getEndDate());
			}
		
			Integer obsId = null;
			
			if(pd.getGroupConcept() != null)
			{
				obsId = Context.getService(RowPerPatientDataService.class).getDao().getObsValueBetweenDates(pd.getPatientId(), pd.getConcept().getConceptId(), pd.getGroupConcept().getConceptId(), beforeDate.getTime(), afterDate.getTime(), dateOfObs);
			}
			else
			{
				obsId = Context.getService(RowPerPatientDataService.class).getDao().getObsValueBetweenDates(pd.getPatientId(), pd.getConcept().getConceptId(), beforeDate.getTime(), afterDate.getTime(), dateOfObs);
			}
			
			if(obsId != null)
			{
				Obs obResult = Context.getObsService().getObs(obsId);
				
				if(obResult != null)
				{	
					par.setObs(obResult);
				}
			}
		}
		
		return par;
    }
}
