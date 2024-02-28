package com.employee.attendancedetails.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.attendancedetails.model.AttendanceDetails;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceDetails, Long>{

	Optional<AttendanceDetails> findByEmployeeIdAndDate(long employeeId, Date date);

	

}
