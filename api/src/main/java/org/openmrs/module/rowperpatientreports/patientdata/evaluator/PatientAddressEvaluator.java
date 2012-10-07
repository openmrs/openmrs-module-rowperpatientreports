package org.openmrs.module.rowperpatientreports.patientdata.evaluator;


import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.PatientAddress;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientAttributeResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={PatientAddress.class})
public class PatientAddressEvaluator implements RowPerPatientDataEvaluator{
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
	    
		PatientAttributeResult result = new PatientAttributeResult(patientsData, context);
		PatientAddress pd = (PatientAddress)patientsData;
		
		Patient patient = pd.getPatient();
	
		PersonAddress address = patient.getPersonAddress();
		
		StringBuilder resultStr = new StringBuilder();
		
		if(address != null)
		{
			if(pd.isIncludeCountry() && address.getCountry() != null)
			{
				resultStr.append(" ");
				resultStr.append(address.getCountry());
			}
			
			if(pd.isIncludeProvince() && address.getStateProvince() != null)
			{
				resultStr.append(" ");
				resultStr.append(address.getStateProvince());
			}
			
			if(pd.isIncludeDistrict() && address.getCountyDistrict() != null)
			{
				resultStr.append(" ");
				resultStr.append(address.getCountyDistrict());
			}
			
			if(pd.isIncludeSector() && address.getCityVillage() != null)
			{
				resultStr.append(" ");
				resultStr.append(address.getCityVillage());
			}
			
			if(pd.isIncludeCell() && address.getNeighborhoodCell() != null)
			{
				resultStr.append(" ");
				resultStr.append(address.getNeighborhoodCell());
			}
			
			if(pd.isIncludeUmudugudu() && address.getAddress1() != null)
			{
				resultStr.append(" ");
				resultStr.append(address.getAddress1());
			}
		}
		
		result.setValue(resultStr.toString().trim());
		
		return result;
    }
}
