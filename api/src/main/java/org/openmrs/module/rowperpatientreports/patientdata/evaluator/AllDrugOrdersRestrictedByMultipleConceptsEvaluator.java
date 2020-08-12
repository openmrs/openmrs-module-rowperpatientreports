package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.RowPerPatientReportsUtil;
import org.openmrs.module.rowperpatientreports.patientdata.definition.AllDrugOrdersRestrictedByMultipleConcepts;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.AllDrugOrdersResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={AllDrugOrdersRestrictedByMultipleConcepts.class})
public class AllDrugOrdersRestrictedByMultipleConceptsEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		AllDrugOrdersResult par = new AllDrugOrdersResult(patientData, context);
		AllDrugOrdersRestrictedByMultipleConcepts pd = (AllDrugOrdersRestrictedByMultipleConcepts)patientData;
		
		List<DrugOrder> orders = RowPerPatientReportsUtil.getDrugOrdersByPatient(pd.getPatient());
	
        if(orders != null)
        {
			if(pd.getConcepts().size() > 0)
			{
				List<DrugOrder> filtered = new ArrayList<DrugOrder>();
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
						if(pd.getConcepts().contains(drug))
						{
							filtered.add(order);
						}
					}
				}
				par.setValue(filtered);
				
			}
			else
			{
				par.setValue(orders);
			}
		}		
		
		return par;
    }
}
