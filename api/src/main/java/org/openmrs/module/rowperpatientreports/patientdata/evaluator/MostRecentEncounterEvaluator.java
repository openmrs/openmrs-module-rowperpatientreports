package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.MostRecentEncounter;
import org.openmrs.module.rowperpatientreports.patientdata.definition.MostRecentEncounterOfType;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.EncounterResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={MostRecentEncounter.class})
public class MostRecentEncounterEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		EncounterResult par = new EncounterResult(patientData, context);
		MostRecentEncounter pd = (MostRecentEncounter)patientData;
		par.setDateFormat(pd.getDateFormat());
	
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
					if(e.getEncounterType()!=null && e.getEncounterType().getEncounterTypeId() == et.getEncounterTypeId())
					{
						add = true;
					}
				}
				for(Form frm: pd.getForms())
				{
					if(e.getForm()!=null && e.getForm().getFormId() == frm.getFormId())
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

        par.setValue(enc);
		return par;
    }
}
