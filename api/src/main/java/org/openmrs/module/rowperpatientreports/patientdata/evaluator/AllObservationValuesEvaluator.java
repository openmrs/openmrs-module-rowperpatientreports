package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.AllObservationValues;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.AllObservationValuesResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.util.OpenmrsUtil;

@Handler(supports={AllObservationValues.class})
public class AllObservationValuesEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		AllObservationValuesResult par = new AllObservationValuesResult(patientData, context);
		
		AllObservationValues pd = (AllObservationValues)patientData;
		par.setFilter(pd.getOutputFilter());
		par.setMinResultsOutput(pd.getMinResultsOutput());
		par.setDateFormat(pd.getDateFormat());
	
		Concept c = pd.getConcept();
		
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(pd.getPatient(), c);
		
		if(pd.getEndDate() != null || pd.getStartDate() != null)
		{
			List<Obs> filtered = new ArrayList<Obs>();
			for(Obs o: obs)
			{
				if((pd.getStartDate() == null || OpenmrsUtil.compare(o.getObsDatetime(), pd.getStartDate()) >= 0) && (pd.getEndDate() == null || OpenmrsUtil.compare(o.getObsDatetime(), pd.getEndDate()) <= 0))
				{
					filtered.add(o);
				}
			}
			obs = filtered;
		}
		
        if(obs != null)
        {
        	Collections.sort(obs, new Comparator<Obs>() {
            	
            	@Override
    			public int compare(Obs o1, Obs o2) {
    				
            		return o1.getObsDatetime().compareTo(o2.getObsDatetime());
    			}
            });
        	
        	par.setValue(obs);
		}
        
        if(pd.getFilter() != null)
        {
        	par.setValue((List<Obs>)pd.getFilter().filter(par.getValue()));
        }
		
		return par;
    }
}
