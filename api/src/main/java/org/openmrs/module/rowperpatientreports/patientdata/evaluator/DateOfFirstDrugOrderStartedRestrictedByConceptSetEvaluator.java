package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohorderentrybridge.api.MoHOrderEntryBridgeService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfFirstDrugOrderStartedRestrictedByConceptSet;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.util.OpenmrsUtil;

@Handler(supports={DateOfFirstDrugOrderStartedRestrictedByConceptSet.class})
public class DateOfFirstDrugOrderStartedRestrictedByConceptSetEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult par = new DateResult(patientData, context);
		DateOfFirstDrugOrderStartedRestrictedByConceptSet pd = (DateOfFirstDrugOrderStartedRestrictedByConceptSet)patientData;
		
		par.setFormat(pd.getDateFormat());
		
		List<DrugOrder> orders = Context.getService(MoHOrderEntryBridgeService.class).getDrugOrdersByPatient(pd.getPatient());
		
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
								if(order.getEffectiveStartDate() != null && (pd.getStartDate() == null || OpenmrsUtil.compare(order.getEffectiveStartDate(), pd.getStartDate()) >= 0) && (pd.getEndDate() == null || OpenmrsUtil.compare(order.getEffectiveStartDate(), pd.getEndDate()) <= 0))
								{		
									if(startDrugOrder == null || order.getEffectiveStartDate().before(startDrugOrder.getEffectiveStartDate()))
									{
										startDrugOrder = order;
									}
								}
							}
						}
					}
					if(startDrugOrder != null)
					{
						par.setValue(startDrugOrder.getEffectiveStartDate());
					}
				}
			}
		}		
		
		return par;
    }
}
