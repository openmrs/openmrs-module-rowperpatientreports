package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Program;




public class DateOfAllProgramEnrolment extends BasePatientData implements DateOfPatientData {

	private Program PatientProgram;
	
	private String dateFormat = "yyyy-MM-dd";


    public void setDateFormat(String dateFormat) {
	    this.dateFormat = dateFormat;
    }


	public String getDateFormat() {
	    return dateFormat;
    }

    public Program getPatientProgram() {
    	return PatientProgram;
    }


	
    public void setPatientProgram(Program patientProgram) {
    	PatientProgram = patientProgram;
    }

}
