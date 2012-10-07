package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.AllObservationValues;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.AllObservationValuesResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={AllObservationValues.class})
public class AllObservationValuesEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		AllObservationValuesResult par = new AllObservationValuesResult(patientData, context);
		AllObservationValues pd = (AllObservationValues)patientData;
		par.setFilter(pd.getOutputFilter());
	
		Concept c = pd.getConcept();
		
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), c);
		
		
        if(obs != null)
        {
        	par.setValue(obs);
		}
        
        if(pd.getFilter() != null)
        {
        	par.setValue((List<Obs>)pd.getFilter().filter(par.getValue()));
        }
		
		return par;
    }
}
