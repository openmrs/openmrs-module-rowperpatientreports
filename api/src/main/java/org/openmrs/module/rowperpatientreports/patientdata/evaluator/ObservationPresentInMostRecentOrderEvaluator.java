package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.DrugOrder;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohorderentrybridge.api.MoHOrderEntryBridgeService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.ObservationPresentInMostRecentOrder;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={ObservationPresentInMostRecentOrder.class})
public class ObservationPresentInMostRecentOrderEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		ObservationResult par = new ObservationResult(patientData, context);
		
		ObservationPresentInMostRecentOrder pd = (ObservationPresentInMostRecentOrder)patientData;
		
		List<DrugOrder> orders = Context.getService(MoHOrderEntryBridgeService.class).getDrugOrdersByPatient(pd.getPatient());
	
        if(orders != null)
        {
        	//filter if the orders are restricted by concept
			if(pd.getOrderConcept() != null)
			{
				Iterator<DrugOrder> it = orders.iterator();
				while (it.hasNext()) {

					Order order = it.next();
					if(order.getConcept().getId() != pd.getOrderConcept().getId())
					{
						it.remove();
					}
				}
			}
			
			//now filter on encounter type
			if(pd.getEncounterType() != null)
			{
				Iterator<DrugOrder> it = orders.iterator();
				while (it.hasNext()) {

					Order order = it.next();
					if(order.getEncounter().getEncounterType().getId() != pd.getEncounterType().getId())
					{
						it.remove();
					}
				}
			}
			
			//now order to get most recent
			Order mostOrder = null;
	      
			//find the most recent value
			for(Order o: orders)
			{
				 if (mostOrder == null
	                     || o.getDateCreated().compareTo(mostOrder.getDateCreated()) > 0)
				 {
					 mostOrder = o;
				 }
			}
	       
			if(mostOrder != null)
			{
				Obs obsResult = null;
				Set<Obs> allObsInEnc = mostOrder.getEncounter().getObs();
				
				for(Obs ob: allObsInEnc)
				{
					if(ob.getConcept().getId() == pd.getObservationConcept().getId())
					{
						obsResult = ob;
					}
				}
				
				if(obsResult != null)
				{
					par.setDateOfObservation(obsResult.getObsDatetime());
					par.setObs(obsResult);
				}
			}
        }
        
        if(pd.getFilter() != null)
    	{
    		par.setResultFilter(pd.getFilter());
    	}
       
        return par;		
    }
}
