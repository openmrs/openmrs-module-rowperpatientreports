package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FirstRecordedObservationWithCodedConceptAnswer;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={FirstRecordedObservationWithCodedConceptAnswer.class})
public class FirstRecordedObservationWithCodedConceptAnswerEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		ObservationResult par = new ObservationResult(patientData, context);
		
		FirstRecordedObservationWithCodedConceptAnswer pd = (FirstRecordedObservationWithCodedConceptAnswer)patientData;
		
			
		Concept question = pd.getQuestion();
		Concept answer = pd.getAnswerRequired();
		
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), question);
		
		Obs ob = null;
        if(obs != null)
        {
			//find the most recent value
			for(Obs o:obs)
			{
				if(o.getValueCoded() != null && o.getValueCoded().getConceptId() == answer.getConceptId())
				{
					if (ob == null
							|| ob.getObsDatetime().compareTo(o.getObsDatetime()) > 0)
					{	 
						ob = o;
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
