package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.DrugOrder;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohorderentrybridge.api.MoHOrderEntryBridgeService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.BaselineDrugOrder;
import org.openmrs.module.rowperpatientreports.patientdata.definition.BaselineObservation;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DrugOrdersResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;
import org.openmrs.util.OpenmrsUtil;

@Handler(supports={BaselineDrugOrder.class})
public class BaselineDrugOrderEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
	    
		DrugOrdersResult par = new DrugOrdersResult(patientData, context);
		
		BaselineDrugOrder pd = (BaselineDrugOrder)patientData;
		par.setDateFormat(pd.getDateFormat());
	
		Mapped<RowPerPatientData> mapped = pd.getDateOfPatientData();
		DateOfPatientData definition = (DateOfPatientData)mapped.getParameterizable();
		definition.setPatientId(pd.getPatientId());
		definition.setPatient(pd.getPatient());
		mapped.setParameterizable(definition);
		
		PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(mapped, context);
		
		Date dateOfObs = (Date)patientDataResult.getValue();
		
		if(dateOfObs != null)
		{
			if(pd.getOffset() > 0)
			{
				Calendar adjusted = Calendar.getInstance();
				adjusted.setTime(dateOfObs);
				adjusted.add(pd.getOffsetType(), pd.getOffset());
				
				dateOfObs = adjusted.getTime();
			}
			
			Calendar beforeDate = Calendar.getInstance();
			beforeDate.setTime(dateOfObs);
			beforeDate.add(Calendar.DAY_OF_YEAR, -pd.getBefore());
			
			Calendar afterDate = Calendar.getInstance();
			afterDate.setTime(dateOfObs);
			afterDate.add(Calendar.DAY_OF_YEAR, pd.getAfter());
		
			if(pd.getDrugConcept() != null)
			{
				List<DrugOrder> orders = Context.getService(MoHOrderEntryBridgeService.class).getDrugOrdersByPatient(pd.getPatient());
				
				for(DrugOrder order: orders)
				{
					if(order.getDrug() != null && order.getDrug().getConcept() != null && order.getDrug().getConcept().equals(pd.getDrugConcept()))
					{
						if((pd.getStartDate() == null || OpenmrsUtil.compare(order.getEffectiveStartDate(), pd.getStartDate()) >=0) && (pd.getEndDate() == null || OpenmrsUtil.compare(order.getEffectiveStartDate(), pd.getEndDate()) <=0))
						{
							if(OpenmrsUtil.compare(afterDate.getTime(), order.getEffectiveStartDate()) >=0)
							{
								if(order.isActive() || order.getAutoExpireDate() == null) 
								{
									par.setValue(order);
								}
								else if(OpenmrsUtil.compare(order.getEffectiveStopDate(), beforeDate.getTime()) >=0)
								{
									par.setValue(order);
								}
								else if(OpenmrsUtil.compare(order.getAutoExpireDate(), beforeDate.getTime()) >=0)
								{
									par.setValue(order);
								}
							}
						}
					}
				}
			}
		}
		return par;
    }
}
