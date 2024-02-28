package com.employee.attendancedetails.component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.employee.attendance.model.Employee;
import com.employee.attendancedetails.model.AttendanceDetails;
import com.employee.attendancedetails.repository.AttendanceRepository;

@Component
public class KafkaConsumer {
	
	@Autowired
	AttendanceRepository repo;
	
	@KafkaListener(topics = "emp-topic", containerFactory = "bookListener")
	public void consume(Employee emp) {
		Optional<AttendanceDetails> det= repo.findByEmployeeIdAndDate(emp.getEmployeeId(),emp.getDate());
		LocalDateTime tempDateTime = LocalDateTime.from(emp.getLoginTime());
		long hours = tempDateTime.until( emp.getLogoutTime(), ChronoUnit.HOURS );
		String status = (hours<4)?"absent":(hours<8 && hours>=4)?"half-day":"present";
		if(det.isPresent()) {
			det.get().setLogoutTime(emp.getLogoutTime());
			det.get().setDuration(hours);
			det.get().setStatus(status);
			repo.save(det.get());
		}else {
			AttendanceDetails attendance = new AttendanceDetails();
			attendance.setId(emp.getId());
			attendance.setEmployeeId(emp.getEmployeeId());
			attendance.setFirstName(emp.getFirstName());
			attendance.setLastName(emp.getLastName());
			attendance.setLoginTime(emp.getLoginTime());
			attendance.setLogoutTime(emp.getLogoutTime());
			attendance.setDate(emp.getDate());
			attendance.setDuration(hours);
			attendance.setStatus(status);
			repo.save(attendance);
		}
		// Print statement
		System.out.println("message = " + emp.getLastName());
		System.out.println("message = " + emp.getDate());
	}
}
