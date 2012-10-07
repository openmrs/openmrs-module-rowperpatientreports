package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FirstDrugOrderStartedRestrictedByConceptSet;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DrugOrdersResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={FirstDrugOrderStartedRestrictedByConceptSet.class})
public class FirstDrugOrderStartedRestrictedByConceptSetEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DrugOrdersResult par = new DrugOrdersResult(patientData, context);
		FirstDrugOrderStartedRestrictedByConceptSet pd = (FirstDrugOrderStartedRestrictedByConceptSet)patientData;
		
		par.setDateFormat(pd.getDateFormat());
		
		List<DrugOrder> orders = Context.getOrderService().getDrugOrdersByPatient(pd.getPatient());
		
        if(orders != null)
        {   	
			if(pd.getDrugConceptSetConcept() != null)
			{
				List<Concept> drugConcepts = Context.getConceptService().getConceptsByConceptSet(pd.getDrugConceptSetConcept());
				if(drugConcepts != null)
				{
					DrugOrder startDrugOrder = null;
					for(DrugOrder order: orders)
					{
						Concept drug = null;
						try{
							drug = order.getDrug().getConcept();
						}
						catch(Exception e)
						{
							log.error("Unable to retrieve a drug from the drug order: " + e.getMessage());
						}
						if(drug != null)
						{
							if(drugConcepts.contains(drug))
							{
								if(order.getStartDate() != null)
								{		
									if(startDrugOrder == null || order.getStartDate().before(startDrugOrder.getStartDate()))
									{
										startDrugOrder = order;
									}
								}
							}
						}
					}
					par.setValue(startDrugOrder);
				}
			}
		}		
		
		return par;
    }
}
