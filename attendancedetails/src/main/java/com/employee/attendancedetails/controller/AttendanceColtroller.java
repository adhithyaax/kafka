package com.employee.attendancedetails.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.attendancedetails.model.AttendanceDetails;
import com.employee.attendancedetails.repository.AttendanceRepository;

import reactor.core.publisher.Flux;


@RestController
public class AttendanceColtroller {
	@Autowired
	AttendanceRepository repo;

	@GetMapping("/attendance")
	public Flux<AttendanceDetails> getAttendanceDetails( @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<AttendanceDetails> a=repo.findAll(paging);
		System.out.println("totalpages->"+a.getTotalPages());
		
		return Flux.fromIterable(a);
	}
	
}
