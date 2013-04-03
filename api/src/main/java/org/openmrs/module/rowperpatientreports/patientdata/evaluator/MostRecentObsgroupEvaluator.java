package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.MostRecentObsgroup;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={MostRecentObsgroup.class})
public class MostRecentObsgroupEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		ObservationResult par = new ObservationResult(patientData, context);
		MostRecentObsgroup pd = (MostRecentObsgroup)patientData;
	
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
        	Set<Obs> members = ob.getGroupMembers();
        	Obs result = null;
        	
        	for(Obs o: members)
    		{
    			if(o.getConcept().equals(pd.getMemberToDisplay()))
    			{
    				result = o;
    			}
    		}
       
        	
        	if(pd.getFilter() != null)
        	{
        		par.setResultFilter(pd.getFilter());
        	}
			
        	if(result != null)
        	{
				par.setObs(result);
				par.setDateOfObservation(result.getObsDatetime());
        	}
		}		
		
		return par;
    }
}
