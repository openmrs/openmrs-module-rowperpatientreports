package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfMostRecentEncounterOfType;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={DateOfMostRecentEncounterOfType.class})
public class DateOfMostRecentEncounterOfTypeEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult par = new DateResult(patientData, context);
		DateOfMostRecentEncounterOfType pd = (DateOfMostRecentEncounterOfType)patientData;
		par.setFormat(pd.getDateFormat());
	
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
        	par.setValue(enc.getEncounterDatetime());
        }
		return par;
    }
}
