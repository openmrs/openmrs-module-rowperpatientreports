package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FirstRecordedObservation;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FirstRecordedObservationWithCodedConceptAnswer;
import org.openmrs.module.rowperpatientreports.patientdata.definition.ObsAfterPeriodOfTimeFromEncounterDate;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={ObsAfterPeriodOfTimeFromEncounterDate.class})
public class ObsAfterPeriodOfTimeFromEncounterDateEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateValueResult par = new DateValueResult(patientData, context);
		
		ObsAfterPeriodOfTimeFromEncounterDate pd = (ObsAfterPeriodOfTimeFromEncounterDate)patientData;
		
			
		Concept question = pd.getQuestion();
		
		Encounter firstEncounter=null;
		
		List<Encounter> encounters=Context.getEncounterService().getEncounters(pd.getPatient(), null, null, null, pd.getForms(), pd.getEncounterTypes(), null, null, null, true);
		
		
		for (Encounter encounter : encounters) {
			if(firstEncounter==null){
				firstEncounter=encounter;
			}
			
			if(firstEncounter.getEncounterDatetime().compareTo(encounter.getEncounterDatetime())>0){
				firstEncounter=encounter;
			}
			
		}	
		
		
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), question);
		
		
		Obs tempOb=null;
		for (int j = 0; j < obs.size(); j++) {	
		for (int i = 0; i < obs.size(); i++) {
			if(i+1 < obs.size() && obs.get(i).getObsDatetime().compareTo(obs.get(i+1).getObsDatetime())>0){
				tempOb=obs.get(i);
				obs.set(i, obs.get(i+1));
				obs.set(i+1, tempOb);			
			}
		}
		}
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		if(firstEncounter!=null){
			cal1.setTime(firstEncounter.getEncounterDatetime());
		}		
		
		
		Obs ob = null;
		
		
		if(obs != null && obs.size()>0)
	    {
			
			for(Obs o:obs)
			{
				cal2.setTime(o.getObsDatetime());
				
				if(cal2.getTimeInMillis()-cal1.getTimeInMillis() >= pd.getTimeDiff() ) {   //15552000000L milliseconds is equivalent to 6 months
					ob=o;
					break;
				}				
			}
	    }
		
		
		
		
        /*if(obs != null && pd.getForms()!=null && pd.getForms().size()!=0)
        {
			//find first recorded obs
			for(Obs o:obs)
			{
				
					if (ob == null || ob.getObsDatetime().compareTo(o.getObsDatetime()) > 0)
					{	 
						for (Form form : pd.getForms()) {
								if(o.getEncounter().getForm().getFormId()==form.getFormId()){
									ob = o;
								}
							}						
					}
			}
        }else*/ 
		
		
	/*	if(obs != null)
        {
			//find first recorded obs
			for(Obs o:obs)
			{
				
					if (ob == null || ob.getObsDatetime().compareTo(o.getObsDatetime()) > 0)
					{						
						ob = o;
					
					}
			}
        }*/
        
        
        
        
        
        
        
        if(ob != null)
        {
        	par.setDateOfObservation(ob.getObsDatetime());	
        }

        if(ob != null && pd.getFilter() != null)
        {
        	String value=(String)pd.getFilter().filter(ob);
        	par.setValue(value);
        	}
        	
        else if(ob != null){	
			par.setValue(ob.getConcept().getName().toString());
		}	
		
		return par;
    }
}
