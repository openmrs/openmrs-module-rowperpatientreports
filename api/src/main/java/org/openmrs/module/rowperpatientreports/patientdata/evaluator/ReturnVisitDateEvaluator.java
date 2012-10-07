package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.ReturnVisitDate;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={ReturnVisitDate.class})
public class ReturnVisitDateEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
	    
		ObservationResult par = new ObservationResult(patientsData, context);
		ReturnVisitDate pd = (ReturnVisitDate)patientsData;
	
		Concept c = pd.getConcept();
		
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), c);
		
		par.setDateFormat(pd.getDateFormat());
		
        if(obs != null)
        {
        	Obs ob = null;
			//find the most recent value
			for(Obs o:obs)
			{
				 if (ob == null
	                     || o.getObsDatetime().compareTo(ob.getObsDatetime()) > 0)
				 {
					 ob = o;
				 }
			}
			if(ob != null)
			{
				par.setObs(ob);
			}
        }		
		
		return par;
    }
}
