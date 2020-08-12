package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DatesOfVisitsByStartDateAndEndDate;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.parameter.EncounterSearchCriteriaBuilder;

@Handler(supports={DatesOfVisitsByStartDateAndEndDate.class})
public class DatesOfVisitsByStartDateAndEndDateEvaluator implements RowPerPatientDataEvaluator{
public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
	
		DateValueResult result = new DateValueResult(patientsData, context);
		DatesOfVisitsByStartDateAndEndDate pd = (DatesOfVisitsByStartDateAndEndDate)patientsData;
		List<Encounter> encounters=null;
		if(pd.getEncounterTypes()==null){
		encounters=Context.getEncounterService().getEncountersByPatient(pd.getPatient());
	    }else{
			EncounterSearchCriteriaBuilder builder = new EncounterSearchCriteriaBuilder();
			builder.setPatient(pd.getPatient()).setEncounterTypes(pd.getEncounterTypes()).setIncludeVoided(false);
			encounters=Context.getEncounterService().getEncounters(builder.createEncounterSearchCriteria());
	    }	    	
		
		Date endDate=(Date)context.getParameterValue("endDate");
		Date startDate=(Date)context.getParameterValue("startDate");
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		
		Calendar encCal = Calendar.getInstance();
		
		StringBuilder encountersDateTime=new StringBuilder();
		
		if(encounters.size() > 0)
		{
			//find date of each encounter
			int i=0;
			for(Encounter e:encounters)
			{
				encCal.setTime(e.getEncounterDatetime());
				if(i!=0){
					encountersDateTime.append("||");
				}
				if(encCal.getTimeInMillis() >= cal1.getTimeInMillis() && encCal.getTimeInMillis() <= cal2.getTimeInMillis()){
					
					String[] encsDateTime=(e.getEncounterDatetime().toString()).split(" 00:00:00");					
					encountersDateTime.append(encsDateTime[0]);
					i++;
				}				
			}			
		}		
		
		if(pd.getFilter() != null && encountersDateTime != null)
		{
			encountersDateTime = (StringBuilder)pd.getFilter().filter(encountersDateTime);
			result.setValue(encountersDateTime.toString());	
		}
		else if(encountersDateTime != null)
		{
			result.setValue(encountersDateTime.toString());
		}
			
		return result;
    }
}
