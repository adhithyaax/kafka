package com.employee.attendancedetails.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.employee.attendance.model.Employee;
import com.employee.attendancedetails.model.AttendanceDetails;
import com.employee.attendancedetails.repository.AttendanceRepository;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Test
    void testConsume() {
    	Date date = new Date();   
        Employee emp = new Employee();
        emp.setId(1L);
        emp.setEmployeeId(1);
        emp.setFirstName("John");
        emp.setLastName("Doe");
        LocalDateTime loginTime = LocalDateTime.now().minusHours(4);
        LocalDateTime logoutTime = LocalDateTime.now();
        emp.setLoginTime(loginTime);
        emp.setLogoutTime(logoutTime);
        emp.setDate(date);

        AttendanceDetails attendanceDetails = new AttendanceDetails();
        attendanceDetails.setId(emp.getId());
        attendanceDetails.setEmployeeId(emp.getEmployeeId());
        attendanceDetails.setFirstName(emp.getFirstName());
        attendanceDetails.setLastName(emp.getLastName());
        attendanceDetails.setLoginTime(emp.getLoginTime());
        attendanceDetails.setLogoutTime(emp.getLogoutTime());
        attendanceDetails.setDate(emp.getDate());
        attendanceDetails.setDuration(4); // Assuming 4 hours
        attendanceDetails.setStatus("half-day"); // Assuming half-day

        when(attendanceRepository.findByEmployeeIdAndDate(anyLong(), any(Date.class)))
            .thenReturn(Optional.empty());
        when(attendanceRepository.save(any(AttendanceDetails.class)))
            .thenReturn(attendanceDetails);

        kafkaConsumer.consume(emp);

        verify(attendanceRepository, times(1)).save(any(AttendanceDetails.class));
    }
}
