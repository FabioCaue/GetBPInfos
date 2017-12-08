package com.ibm.wam.model.beans;

import org.springframework.util.StringUtils;

import com.ibm.bpm.bluepages.BPHrOrg;

public class BeanUsuarioBPLogado {

	private String firstName;
	private String lastName;
	private String notesMail;
	private String email;
	private String division;
	private String department;
	private String serialNumber;
	private boolean manager;
	private String managerEmail;
	private String managerNotesMail;
	private String managerSerialNumber;
	private BPHrOrg bpHrOrg;
	private String hrOrganizationCode;
	private String fullName;
	private boolean isVendor;
	private String intranetID;
	
	/**
	 * @return the intranetID
	 */
	public String getIntranetID() {
		return intranetID;
	}
	/**
	 * @param intranetID the intranetID to set
	 */
	public void setIntranetID(String intranetID) {
		this.intranetID = intranetID;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the isVendor
	 */
	public boolean isVendor() {
		return isVendor;
	}
	/**
	 * @param isVendor the isVendor to set
	 */
	public void setVendor(boolean isVendor) {
		this.isVendor = isVendor;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the notesMail
	 */
	public String getNotesMail() {
		return notesMail;
	}
	/**
	 * @param notesMail the notesMail to set
	 */
	public void setNotesMail(String notesMail) {
		this.notesMail = notesMail;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the division
	 */
	public String getDivision() {
		return division;
	}
	/**
	 * @param division the division to set
	 */
	public void setDivision(String division) {
		this.division = division;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the manager
	 */
	public boolean isManager() {
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	/**
	 * @return the managerEmail
	 */
	public String getManagerEmail() {
		return managerEmail;
	}
	/**
	 * @param managerEmail the managerEmail to set
	 */
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	/**
	 * @return the managerNotesMail
	 */
	public String getManagerNotesMail() {
		return managerNotesMail;
	}
	/**
	 * @param managerNotesMail the managerNotesMail to set
	 */
	public void setManagerNotesMail(String managerNotesMail) {
		this.managerNotesMail = managerNotesMail;
	}
	/**
	 * @return the managerSerialNumber
	 */
	public String getManagerSerialNumber() {
		return managerSerialNumber;
	}
	/**
	 * @param managerSerialNumber the managerSerialNumber to set
	 */
	public void setManagerSerialNumber(String managerSerialNumber) {
		this.managerSerialNumber = managerSerialNumber;
	}
	/**
	 * @return the bpHrOrg
	 */
	public BPHrOrg getBpHrOrg() {
		return bpHrOrg;
	}
	/**
	 * @param bpHrOrg the bpHrOrg to set
	 */
	public void setBpHrOrg(BPHrOrg bpHrOrg) {
		this.bpHrOrg = bpHrOrg;
	}
	/**
	 * @return the hrOrganizationCode
	 */
	public String getHrOrganizationCode() {
		return hrOrganizationCode;
	}
	
	/**
	 * Sets the hr organization code that is reported in the ibmPerson object.
	 * In most cases this value should match the data contained within the
	 * bpHrOrg object associated with this object. However, there have been
	 * cases where the corresponding ibmOrganization object does not exist (yet)
	 * in ED. In such cases, you will see a value for the hrOrganizationCode
	 * instance variable and no value (NULL) for the BPHrOrg instance variable.
	 * 
	 * @param hrOrganizationCode
	 */
	public void setHrOrganizationCode(String hrOrganizationCode) {
		this.hrOrganizationCode = hrOrganizationCode;
	}

	public boolean isValid() {
		if (bpHrOrg != null
				&& StringUtils.hasText(bpHrOrg.getHrOrganizationCode())
				&& StringUtils.hasText(getEmail())
				&& StringUtils.hasText(getNotesMail())) {
			return true;
		} else {
			return false;
		}
	}
}
