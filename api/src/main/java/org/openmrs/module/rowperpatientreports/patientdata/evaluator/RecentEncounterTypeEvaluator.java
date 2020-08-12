package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RecentEncounterType;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.parameter.EncounterSearchCriteriaBuilder;

@Handler(supports={RecentEncounterType.class})
public class RecentEncounterTypeEvaluator implements RowPerPatientDataEvaluator{
public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
	
		DateValueResult result = new DateValueResult(patientsData, context);
		RecentEncounterType pd = (RecentEncounterType)patientsData;
		List<Encounter> encounters=null;
		if(pd.getEncounterTypes()==null){
		encounters=Context.getEncounterService().getEncountersByPatient(pd.getPatient());
	    }else{
			EncounterSearchCriteriaBuilder builder = new EncounterSearchCriteriaBuilder();
			builder.setPatient(pd.getPatient()).setEncounterTypes(pd.getEncounterTypes()).setIncludeVoided(false);
	    	encounters=Context.getEncounterService().getEncounters(builder.createEncounterSearchCriteria());
	    }
	    	
		String encountertype="";
		Encounter enc = null;
		if(encounters.size() > 0)
		{
			//find the most recent value
			for(Encounter e:encounters)
			{
				 if (enc == null
	                     || e.getEncounterDatetime().compareTo(enc.getEncounterDatetime()) > 0)
				 {
					 enc = e;
				 }
			}
			
			encountertype=enc.getEncounterType().getName();
		}
		
		if(enc != null)
		{
			result.setDateOfObservation(enc.getEncounterDatetime());
		}
		
		if(pd.getFilter() != null && enc != null)
		{
			encountertype = (String)pd.getFilter().filter(encountertype);
			result.setValue(encountertype);	
		}
		else if(enc != null)
		{
			result.setValue(encountertype);
		}
			
		return result;
    }
}
