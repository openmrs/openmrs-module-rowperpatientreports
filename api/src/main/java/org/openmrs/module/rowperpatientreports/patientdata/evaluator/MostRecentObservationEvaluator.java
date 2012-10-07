package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.MostRecentObservation;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={MostRecentObservation.class})
public class MostRecentObservationEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		ObservationResult par = new ObservationResult(patientData, context);
		MostRecentObservation pd = (MostRecentObservation)patientData;
	
		Concept c = pd.getConcept();
		
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), c);
		
		Obs ob = null;
        if(obs != null)
        {
			//find the most recent value
			for(Obs o:obs)
			{ 
				if (ob == null
	                     || o.getObsDatetime().compareTo(ob.getObsDatetime()) > 0)
				 {
					 	if(pd.isIncludeNull())
					 	{
					 		ob = o;
					 	}
					 	else
					 	{
					 		String value = o.getValueAsString(Context.getLocale());
					 		if(value != null && value.trim().length() > 0)
					 		{
					 			ob = o;
					 		}
					 	}
				 }
			}
        }

        if(ob != null)
        {
        	
        	if(pd.getFilter() != null)
        	{
        		par.setResultFilter(pd.getFilter());
        	}
			
			par.setDateOfObservation(ob.getObsDatetime());
			par.setObs(ob);
		}		
		
		return par;
    }
}
