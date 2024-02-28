package com.employee.attendancedetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.attendancedetails.model.AttendanceDetails;
import com.employee.attendancedetails.repository.AttendanceRepository;

import reactor.core.publisher.Flux;


@RestController
public class AttendanceColtroller {
	@Autowired
	AttendanceRepository repo;

	@GetMapping("/attendance")
	public Flux<AttendanceDetails> getAttendanceDetails() {
		return Flux.fromIterable(repo.findAll());
	}
	
}
