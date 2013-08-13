package com.slalom.calllog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class CallLog {

	public CallLog() {

		Date date = new Date();
		callDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
		callHour = new SimpleDateFormat("hh").format(date);
		callMinute = new SimpleDateFormat("mm").format(date);
		callAmPm = new SimpleDateFormat("a").format(date);
	}

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String lengthOfCall;
	private String volunteerId;
	private String firstName;
	private String lastName;
	private String callDate;
	private String callHour;
	private String callMinute;
	private String callAmPm;
	private String callerPhoneNumber;

	public String getCallerPhoneNumber() {
		return callerPhoneNumber;
	}

	public void setCallerPhoneNumber(String callerPhoneNumber) {
		this.callerPhoneNumber = callerPhoneNumber;
	}

	private String description;
	private String referral;
	private String referralOther;
	private String leadSource;
	private String leadSourceOther;
	private String quotes;
	private Set<String> callTopic;
	private String callTopicOther;
	private String callStatus;
	private String callStatusOther;

	public String getCallStatusOther() {
		return callStatusOther;
	}

	public void setCallStatusOther(String callStatusOther) {
		this.callStatusOther = callStatusOther;
	}

	private String callerIs;
	private String callerIsOther;
	private String callerZip;
	private Set<String> literature;
	private String literatureOther;
	private String contactAddress1;
	private String contactAddress2;
	private String contactCity;
	private String contactState;
	private String contactZip;
	private String contactEmail;

	public String getLengthOfCall() {
		return lengthOfCall;
	}

	public void setLengthOfCall(String lengthOfCall) {
		this.lengthOfCall = lengthOfCall;
	}

	public String getVolunteerId() {
		return volunteerId;
	}

	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReferralOther() {
		return referralOther;
	}

	public void setReferralOther(String referralOther) {
		this.referralOther = referralOther;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getLeadSourceOther() {
		return leadSourceOther;
	}

	public void setLeadSourceOther(String leadSourceOther) {
		this.leadSourceOther = leadSourceOther;
	}

	public String getQuotes() {
		return quotes;
	}

	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	public Set<String> getCallTopic() {
		return callTopic;
	}

	public void setCallTopic(Set<String> callTopic) {
		this.callTopic = callTopic;
	}

	public String getCallTopicOther() {
		return callTopicOther;
	}

	public void setCallTopicOther(String callTopicOther) {
		this.callTopicOther = callTopicOther;
	}

	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	public String getCallerIs() {
		return callerIs;
	}

	public void setCallerIs(String callerIs) {
		this.callerIs = callerIs;
	}

	public String getCallerIsOther() {
		return callerIsOther;
	}

	public void setCallerIsOther(String callerIsOther) {
		this.callerIsOther = callerIsOther;
	}

	public String getCallerZip() {
		return callerZip;
	}

	public void setCallerZip(String callerZip) {
		this.callerZip = callerZip;
	}

	public Set<String> getLiterature() {
		return literature;
	}

	public void setLiterature(Set<String> literature) {
		this.literature = literature;
	}

	public String getLiteratureOther() {
		return literatureOther;
	}

	public void setLiteratureOther(String literatureOther) {
		this.literatureOther = literatureOther;
	}

	public String getContactAddress1() {
		return contactAddress1;
	}

	public void setContactAddress1(String contactAddress1) {
		this.contactAddress1 = contactAddress1;
	}

	public String getContactAddress2() {
		return contactAddress2;
	}

	public void setContactAddress2(String contactAddress2) {
		this.contactAddress2 = contactAddress2;
	}

	public String getContactCity() {
		return contactCity;
	}

	public void setContactCity(String contactCity) {
		this.contactCity = contactCity;
	}

	public String getContactState() {
		return contactState;
	}

	public void setContactState(String contactState) {
		this.contactState = contactState;
	}

	public String getContactZip() {
		return contactZip;
	}

	public void setContactZip(String contactZip) {
		this.contactZip = contactZip;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getReferral() {
		return referral;
	}

	public void setReferral(String referral) {
		this.referral = referral;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

	public String getCallHour() {
		return callHour;
	}

	public void setCallHour(String callHour) {
		this.callHour = callHour;
	}

	public String getCallMinute() {
		return callMinute;
	}

	public void setCallMinute(String callMinute) {
		this.callMinute = callMinute;
	}

	public String getCallAmPm() {
		return callAmPm;
	}

	public void setCallAmPm(String callAmPm) {
		this.callAmPm = callAmPm;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
