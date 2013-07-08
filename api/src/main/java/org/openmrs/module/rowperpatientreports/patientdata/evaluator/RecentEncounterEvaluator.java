package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RecentEncounter;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={RecentEncounter.class})
public class RecentEncounterEvaluator implements RowPerPatientDataEvaluator{
public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
	
		DateValueResult result = new DateValueResult(patientsData, context);
		RecentEncounter pd = (RecentEncounter)patientsData;
		//List<Encounter> encounters= Context.getEncounterService().getEncountersByPatient(pd.getPatient());
		List<Encounter> encounters=Context.getEncounterService().getEncounters(pd.getPatient(), null, null, null, pd.getForms(), pd.getEncounterTypes(), null, null, null, false);
	    	
		String encountertype="";
		Encounter enc = null;
		if(encounters.size() > 0)
		{
			//find the most recent value
			for(Encounter e:encounters)
			{
				 
			//if(pd.getEncounterTypes() != null){
				for (EncounterType et : pd.getEncounterTypes()) {
					if(e.getEncounterType()!=null && e.getEncounterType().getEncounterTypeId() == et.getEncounterTypeId()){
						
						
						if (enc == null || e.getEncounterDatetime().compareTo(enc.getEncounterDatetime()) > 0)
						 {
							 enc = e;
						 }
						
						
					}   
                }
			//}	
			//if(pd.getForms() != null){
				for (Form frm : pd.getForms()) {
					if(e.getForm()!=null && e.getForm().getFormId() == frm.getFormId()){
						
						
						if (enc == null || e.getEncounterDatetime().compareTo(enc.getEncounterDatetime()) > 0)
						 {
							 enc = e;
						 }
						
						
					}   
                }
			//}	
				
				
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
