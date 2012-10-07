package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfMostRecentObservationHavingCodedAnswer;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={DateOfMostRecentObservationHavingCodedAnswer.class})
public class DateOfMostRecentObservationHavingCodedAnswerEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult result = new DateResult(patientData, context);
		DateOfMostRecentObservationHavingCodedAnswer pd = (DateOfMostRecentObservationHavingCodedAnswer)patientData;
	
		List<Obs> obs = new ArrayList<Obs>();
		
		for(Concept c: pd.getConcepts())
		{
			obs.addAll(Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), c));
		}
		
		Obs ob = null;
		//find the most recent value
		for(Obs o:obs)
		{
			boolean add = false;
			
			for(Concept a: pd.getAnswers())
			{
				if(o.getValueCoded().getConceptId().equals(a.getConceptId()))
				{
					add = true;
					break;
				}
			}
			
			if(add)
			{
				if (ob == null
	                     || o.getObsDatetime().compareTo(ob.getObsDatetime()) > 0)
				 {
					 ob = o;
				 }
			}
		}
       

        if(ob != null)
        {
			result.setValue(ob.getObsDatetime());
			result.setFormat(pd.getDateFormat());
		}		
		
		return result;
    }
}
