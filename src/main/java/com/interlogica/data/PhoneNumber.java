package com.interlogica.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "numbers")
public class PhoneNumber {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public PhoneNumber() {
	}

	public PhoneNumber(String id, String phone) {
		this.id = id;
		this.phoneNumber = phone;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "phoneNumber")
	private String phoneNumber;

	@Override
	public String toString() {
		return "PhoneNumber [id=" + id + ", phoneNumber=" + phoneNumber + "]";
	}
}
