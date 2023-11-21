package com.interlogica.data;

public class RowResult {

	private String id;
	
	private String number;
	
	private PhoneNumberResult numberResult = PhoneNumberResult.VALID;
	
	private String note;

	
	public RowResult() {
		
	}
	
	public RowResult(String id, String number) {
		this.id = id;
		this.number = number;
	}
	
	
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public PhoneNumberResult getNumberResult() {
		return numberResult;
	}

	public void setNumberResult(PhoneNumberResult numberResult) {
		this.numberResult = numberResult;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
