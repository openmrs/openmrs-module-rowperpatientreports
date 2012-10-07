package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.MultipleConceptObsValueAfterDateOfOtherDefinition;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={MultipleConceptObsValueAfterDateOfOtherDefinition.class})
public class MultipleConceptObsValueAfterDateOfOtherDefinitionEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
	    
		ObservationResult par = new ObservationResult(patientData, context);
		MultipleConceptObsValueAfterDateOfOtherDefinition pd = (MultipleConceptObsValueAfterDateOfOtherDefinition)patientData;
		
		List<Concept> concepts = pd.getConcepts();
		
		List<Obs> obs = new ArrayList<Obs>();
		
		for(Concept c: concepts)
		{
			List<Obs> cObs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), c);
			if(cObs != null && cObs.size() > 0)
			{
				obs.addAll(cObs);
			}
		}
		
		Mapped<RowPerPatientData> mapped = pd.getDateOfPatientData();
		DateOfPatientData definition = (DateOfPatientData)mapped.getParameterizable();
		definition.setPatient(pd.getPatient());
		definition.setPatientId(pd.getPatientId());
		mapped.setParameterizable(definition);
		
		PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(mapped, context);
		
		Date dateOfObs = (Date)patientDataResult.getValue();
		
		Obs obResult = null;
		
		if(dateOfObs != null)
		{
			Calendar obsResultCal = Calendar.getInstance();
			obsResultCal.setTime(dateOfObs);
			
			for(Obs o: obs)
			{
				Calendar oCal = Calendar.getInstance();
				oCal.setTime(o.getObsDatetime());
				
				if(obResult == null || (oCal.get(Calendar.YEAR) == obsResultCal.get(Calendar.YEAR) && oCal.get(Calendar.DAY_OF_YEAR) == obsResultCal.get(Calendar.DAY_OF_YEAR)) || (o.getObsDatetime().before(obResult.getObsDatetime()) &&  o.getObsDatetime().after(dateOfObs)))
				{
					obResult = o;
				}
			}	
		}
		
		if(obResult != null)
		{
			par.setDateOfObservation(obResult.getObsDatetime());
			par.setObs(obResult);
		}
		
		return par;
    }
}
