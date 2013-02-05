package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.BaselineEncounter;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.EncounterResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={BaselineEncounter.class})
public class BaselineEncounterEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
	    
		EncounterResult par = new EncounterResult(patientData, context);
		
		BaselineEncounter pd = (BaselineEncounter)patientData;
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
		
			Integer encId = null;
			
			List<Integer> encounterIds = new ArrayList<Integer>();
			for(EncounterType et: pd.getEncounterTypes())
			{
				encounterIds.add(et.getId());
			}
 			
			
			encId = Context.getService(RowPerPatientDataService.class).getDao().getEncounterBetweenDates(pd.getPatientId(), encounterIds, beforeDate.getTime(), afterDate.getTime(), dateOfObs);
			
			if(encId != null)
			{
				Encounter encResult = Context.getEncounterService().getEncounter(encId);
				
				if(encResult != null)
				{	
					par.setValue(encResult);
				}
			}
		}
		
		return par;
    }
}
