package com.employee.attendance.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "employee")
public class Employee {
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	private long  id;
	private long  employeeId;
	@Size(max = 100)
	@Indexed(unique = true)
	private String firstName;
	@Size(max = 100)
	@Indexed(unique = true)
	private String lastName;
	@NotNull
	private LocalDateTime loginTime;
	@NotNull
	private LocalDateTime logoutTime;
	
	private Date date;

	public long  getId() {
		return id;
	}

	public void setId(long id) {
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
