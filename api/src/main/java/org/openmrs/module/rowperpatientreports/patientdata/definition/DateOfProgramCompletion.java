package org.openmrs.module.rowperpatientreports.patientdata.definition;




public class DateOfProgramCompletion extends BasePatientData implements DateOfPatientData {

	private int ProgramId;
	
	private String dateFormat = "yyyy-MM-dd";
	
	private boolean returnEarliest = false;


    public void setDateFormat(String dateFormat) {
	    this.dateFormat = dateFormat;
    }


	public String getDateFormat() {
	    return dateFormat;
    }


	public int getProgramId() {
		return ProgramId;
	}


	public void setProgramId(int programId) {
		ProgramId = programId;
	}


	
    public boolean isReturnEarliest() {
    	return returnEarliest;
    }


	
    public void setReturnEarliest(boolean returnEarliest) {
    	this.returnEarliest = returnEarliest;
    }

}
