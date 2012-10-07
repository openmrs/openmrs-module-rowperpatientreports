package org.openmrs.module.rowperpatientreports.patientdata.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class EvaluateForOtherPatientDataResult extends BasePatientDataResult {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	private List<PatientDataResult> value;
	
	public EvaluateForOtherPatientDataResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
		return String.class;
	}
	
	public List<PatientDataResult> getValue() {
		if(value == null)
		{
			value = new ArrayList<PatientDataResult>();
		}
		return value;
	}
	
	public boolean isMultiple() {
		return true;
	}

    
	/**
     * @param value the value to set
     */
    public void setValue(List<PatientDataResult> value) {
    	this.value = value;
    }

    public String getValueAsString() {
	    StringBuilder result = new StringBuilder(" ");
	    
	    for(PatientDataResult p: getValue())
	    {
    		result.append(p.getValueAsString());
    		result.append(" ");
	    }
	    return result.toString().trim();
    }
    
    public void addResult(PatientDataResult result)
    {
    	getValue().add(result);
    }
}
