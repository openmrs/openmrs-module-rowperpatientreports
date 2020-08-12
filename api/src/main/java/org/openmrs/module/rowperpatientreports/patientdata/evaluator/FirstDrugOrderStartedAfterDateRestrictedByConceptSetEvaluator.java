package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.RowPerPatientReportsUtil;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FirstDrugOrderStartedAfterDateRestrictedByConceptSet;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DrugOrdersResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={FirstDrugOrderStartedAfterDateRestrictedByConceptSet.class})
public class FirstDrugOrderStartedAfterDateRestrictedByConceptSetEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
	    
		DrugOrdersResult par = new DrugOrdersResult(patientData, context);
		FirstDrugOrderStartedAfterDateRestrictedByConceptSet pd = (FirstDrugOrderStartedAfterDateRestrictedByConceptSet)patientData;
		
		List<DrugOrder> orders = RowPerPatientReportsUtil.getDrugOrdersByPatient(pd.getPatient());
		
		Mapped<RowPerPatientData> definition = pd.getDateOfPatientData();
		
		PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(definition, context);
		
		Date dateOfObs = (Date)patientDataResult.getValue();
	
        if(orders != null)
        {
			if(dateOfObs != null)
			{
	        	Calendar dateOfOb = Calendar.getInstance();
	        	dateOfOb.setTime(dateOfObs);
	        	
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
									if(order.getEffectiveStartDate() != null)
									{
										Calendar startDate = Calendar.getInstance();
										startDate.setTime(order.getEffectiveStartDate());
										
										if(order.getEffectiveStartDate().after(dateOfObs) && (startDrugOrder == null || (startDate.get(Calendar.YEAR) == dateOfOb.get(Calendar.YEAR) && startDate.get(Calendar.DAY_OF_YEAR) == dateOfOb.get(Calendar.DAY_OF_YEAR)) || order.getEffectiveStartDate().before(startDrugOrder.getEffectiveStartDate())))
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
		}		
		
		return par;
    }
}
