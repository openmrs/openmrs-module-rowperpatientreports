package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.ObservationInMostRecentEncounterOfType;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={ObservationInMostRecentEncounterOfType.class})
public class ObservationInMostRecentEncounterOfTypeEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		ObservationResult par = new ObservationResult(patientData, context);
		ObservationInMostRecentEncounterOfType pd = (ObservationInMostRecentEncounterOfType)patientData;
	
		List<Encounter> encounters = Context.getEncounterService().getEncountersByPatientId(pd.getPatientId());
		
		Encounter enc = null;
        if(encounters != null)
        {
			//find the most recent value
			for(Encounter e:encounters)
			{
				boolean add = false;
				for(EncounterType et: pd.getEncounterTypes())
				{
					if(e.getEncounterType().getEncounterTypeId() == et.getEncounterTypeId())
					{
						add = true;
					}
				}
				
				if(add)
				{
					 if (enc == null
		                     || e.getEncounterDatetime().compareTo(enc.getEncounterDatetime()) > 0)
					 {
						 enc = e;
					 }
				}
			}
        }
        
        if(enc != null)
        {
        	Set<Obs> allObs = enc.getAllObs();
        	if(allObs != null)
        	{
        		for(Obs o: allObs)
        		{
        			if(o.getConcept().getConceptId().equals(pd.getObservationConcept().getConceptId()))
        			{
      
        				if(pd.getFilter() != null)
        	        	{
        	        		par.setResultFilter(pd.getFilter());
        	        	}
    					
    					par.setDateOfObservation(o.getObsDatetime());
    					par.setObs(o);
        				
        			}
        		}
        	}
        }

		return par;
    }
}
