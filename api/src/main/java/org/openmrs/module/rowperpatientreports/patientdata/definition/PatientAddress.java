package org.openmrs.module.rowperpatientreports.patientdata.definition;

public class PatientAddress extends BasePatientData implements RowPerPatientData{

	private boolean includeCountry = true;

	private boolean includeProvince = true;
	
	private boolean includeDistrict = true;
	
	private boolean includeSector = true;
	
	private boolean includeCell = true;
	
	private boolean includeUmudugudu = true;

	
    /**
     * @return the includeCountry
     */
    public boolean isIncludeCountry() {
    	return includeCountry;
    }

	
    /**
     * @param includeCountry the includeCountry to set
     */
    public void setIncludeCountry(boolean includeCountry) {
    	this.includeCountry = includeCountry;
    }

	
    /**
     * @return the includeProvince
     */
    public boolean isIncludeProvince() {
    	return includeProvince;
    }

	
    /**
     * @param includeProvince the includeProvince to set
     */
    public void setIncludeProvince(boolean includeProvince) {
    	this.includeProvince = includeProvince;
    }

	
    /**
     * @return the includeDistrict
     */
    public boolean isIncludeDistrict() {
    	return includeDistrict;
    }

	
    /**
     * @param includeDistrict the includeDistrict to set
     */
    public void setIncludeDistrict(boolean includeDistrict) {
    	this.includeDistrict = includeDistrict;
    }

	
    /**
     * @return the includeSector
     */
    public boolean isIncludeSector() {
    	return includeSector;
    }

	
    /**
     * @param includeSector the includeSector to set
     */
    public void setIncludeSector(boolean includeSector) {
    	this.includeSector = includeSector;
    }

	
    /**
     * @return the includeCell
     */
    public boolean isIncludeCell() {
    	return includeCell;
    }

	
    /**
     * @param includeCell the includeCell to set
     */
    public void setIncludeCell(boolean includeCell) {
    	this.includeCell = includeCell;
    }

	
    /**
     * @return the includeUmudugudu
     */
    public boolean isIncludeUmudugudu() {
    	return includeUmudugudu;
    }

	
    /**
     * @param includeUmudugudu the includeUmudugudu to set
     */
    public void setIncludeUmudugudu(boolean includeUmudugudu) {
    	this.includeUmudugudu = includeUmudugudu;
    }
}
