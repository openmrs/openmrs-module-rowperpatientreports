package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateDiff;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.NumberResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
@Handler(supports={DateDiff.class})
public class DateDiffEvaluator implements RowPerPatientDataEvaluator {
	protected Log log = LogFactory.getLog(DateDiffEvaluator.class);
	public PatientDataResult evaluate(RowPerPatientData patientData,
			EvaluationContext context) throws EvaluationException {
		NumberResult nr=new NumberResult(patientData, context);
		DateDiff pd=(DateDiff)patientData;
		
        Concept c = pd.getConcept();		
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), c);
		Date lastDateOfObs=null;
		if(obs.size() > 0)
		{
			Obs o = obs.get(0);
			lastDateOfObs=o.getObsDatetime();
		}
		
		
		List<Encounter> encounters=null;
		if(pd.getEncounterTypes()==null){
		encounters=Context.getEncounterService().getEncountersByPatient(pd.getPatient());
	    }else{
	    	encounters=Context.getEncounterService().getEncounters(pd.getPatient(), null, null, null, null, pd.getEncounterTypes(), null, false);	    	
	    }
	    	
		Date encounterDate=null;
		Encounter enc = null;
		if(encounters.size() > 0)
		{
			//find the most recent encounter date
			for(Encounter e:encounters)
			{
				 if (enc == null
	                     || e.getEncounterDatetime().compareTo(enc.getEncounterDatetime()) > 0)
				 {
					 enc = e;
				 }
			}			
			encounterDate=enc.getEncounterDatetime();
		}		
		
		Long diff=null;
		Date endDate=(Date)context.getParameterValue("endDate");
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(endDate);
		
		Calendar cal2 = Calendar.getInstance();
		
		
		if(pd.getConcept()!=null && obs.size() > 0){
			cal2.setTime(lastDateOfObs);
			diff=cal1.getTimeInMillis()-cal2.getTimeInMillis();
		}
		else if(pd.getEncounterTypes()!=null && encounters.size() > 0){
			cal2.setTime(encounterDate);
			diff=cal1.getTimeInMillis()-cal2.getTimeInMillis();
		}
		
		if(diff != null)
		{
			switch (pd.getDateDiffType()) {
			 case DAYS:
				 diff=(diff/(1000*60*60*24));
				 nr.setValue(diff);
	             break;
			 case HOURS:
				 diff=(diff/(1000*60*60));
				 nr.setValue(diff);
	             break;
			 case MINUTES:
				 diff=(diff/(1000*60));
				 nr.setValue(diff);
	             break;
			 case MONTHS:
				 diff=(diff/(1000*60*60*24))/30;
				 nr.setValue(diff);
	             break;
			 case SECONDS:
				 diff=(diff/(1000));
				 nr.setValue(diff);
	             break;
			 case WEEKS:
				 diff=(diff/(1000*60*60*24))/7;
				 nr.setValue(diff);
	             break;
			 case YEARS:
				 diff=(diff/(1000*60*60*24))/365;
				 nr.setValue(diff);
	             break;	
			 default: 
	           log.error("Invalid type for date difference");
	             break;
			
			}	
		}
		return nr;
	}

}
