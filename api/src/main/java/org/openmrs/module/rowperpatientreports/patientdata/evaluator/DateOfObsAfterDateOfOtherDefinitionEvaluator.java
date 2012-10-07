package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfObsAfterDateOfOtherDefinition;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={DateOfObsAfterDateOfOtherDefinition.class})
public class DateOfObsAfterDateOfOtherDefinitionEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
	    
		DateResult par = new DateResult(patientData, context);
		
		DateOfObsAfterDateOfOtherDefinition pd = (DateOfObsAfterDateOfOtherDefinition)patientData;
		par.setFormat(pd.getDateFormat());
	
		Mapped<RowPerPatientData> mapped = pd.getDateOfPatientData();
		DateOfPatientData definition = (DateOfPatientData)mapped.getParameterizable();
		definition.setPatientId(pd.getPatientId());
		definition.setPatient(pd.getPatient());
		mapped.setParameterizable(definition);
		
		PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(mapped, context);
		
		Date dateOfObs = (Date)patientDataResult.getValue();
		
		if(dateOfObs != null)
		{
			Integer obsId = null;
			
			if(pd.getGroupConcept() != null)
			{
				obsId = Context.getService(RowPerPatientDataService.class).getDao().getObsValueAfterDate(pd.getPatientId(), pd.getConcept().getConceptId(), pd.getGroupConcept().getConceptId(), dateOfObs);
			}
			else
			{
				obsId = Context.getService(RowPerPatientDataService.class).getDao().getObsValueAfterDate(pd.getPatientId(), pd.getConcept().getConceptId(), dateOfObs);
			}
			
			if(obsId != null)
			{
				Obs obResult = Context.getObsService().getObs(obsId);
				
				if(obResult != null)
				{	
					par.setValue(obResult.getObsDatetime());
				}
			}
		}
		
		return par;
    }
}
