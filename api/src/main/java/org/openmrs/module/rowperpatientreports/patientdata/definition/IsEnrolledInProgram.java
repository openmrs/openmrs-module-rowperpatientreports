package org.openmrs.module.rowperpatientreports.patientdata.definition;




public class IsEnrolledInProgram extends BasePatientData implements DateOfPatientData {

	private int ProgramId = -1;
	
	private ResultFilter filter;


	public int getProgramId() {
		return ProgramId;
	}


	public void setProgramId(int programId) {
		ProgramId = programId;
	}


	public ResultFilter getFilter() {
		return filter;
	}


	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}
}
