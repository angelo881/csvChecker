package com.interlogica.data;

import javax.validation.constraints.Pattern;

public class AfricanPhoneNumber {

	
	@Pattern(regexp = "^278[0-9]{8}$")
	private String number;
	
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public AfricanPhoneNumber() {
		
	}
	
	public AfricanPhoneNumber(String a) {
		this.number = a;
	}
}
