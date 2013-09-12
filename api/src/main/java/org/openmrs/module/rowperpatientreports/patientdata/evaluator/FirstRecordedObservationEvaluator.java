package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FirstRecordedObservation;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FirstRecordedObservationWithCodedConceptAnswer;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={FirstRecordedObservation.class})
public class FirstRecordedObservationEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateValueResult par = new DateValueResult(patientData, context);
		
		FirstRecordedObservation pd = (FirstRecordedObservation)patientData;
		
			
		Concept question = pd.getQuestion();
		
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), question);
		
		Obs ob = null;
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
        }else*/ if(obs != null)
        {
			//find first recorded obs
			for(Obs o:obs)
			{
				
					if (ob == null || ob.getObsDatetime().compareTo(o.getObsDatetime()) > 0)
					{						
						ob = o;
					
					}
			}
        }
        
        
        
        
        
        
        
        if(ob != null)
        {
        	par.setDateOfObservation(ob.getObsDatetime());	
        }

        if(ob != null && pd.getFilter() != null)
        {
        	String obsId=(String)pd.getFilter().filter(ob.getId().toString());
        	//if(pd.getPatient().getPatientId()==42488){
        	log.info("################################: "+obsId+" $$$$$$$$$: "+pd.getPatient().getPatientId());
        	//}
        		par.setValue(obsId);
        	}
        	
        else if(ob != null){	
			par.setValue(ob.getConcept().getName().toString());
		}	
		
		return par;
    }
}
