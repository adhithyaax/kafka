package com.employee.attendance.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import java.util.Date;




public class Employee {
	
	public static final String SEQUENCE_NAME = "users_sequence";
	private BigInteger  id;
	private long  employeeId;
	private String firstName;
	private String lastName;
	private LocalDateTime loginTime;
	private LocalDateTime logoutTime;
	
	private Date date;

	public BigInteger  getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public LocalDateTime getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(LocalDateTime logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	
	
}
