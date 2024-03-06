package com.employee.attendance.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.employee.attendance.exception.ResourceNotFoundException;
import com.employee.attendance.model.Employee;
import com.employee.attendance.repository.EmployeeRepository;
import com.employee.attendance.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private KafkaTemplate<String, Employee> kafkaTemplate;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    private List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        Date date = new Date();   
        employeeList = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setEmployeeId(1);
        emp1.setDate(date);
        emp1.setLoginTime(LocalDateTime.now().minusHours(1));
        emp1.setLogoutTime(LocalDateTime.now());
        employeeList.add(emp1);

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setEmployeeId(2);
        emp2.setDate(date);
        emp2.setLoginTime(LocalDateTime.now().minusHours(2));
        emp2.setLogoutTime(LocalDateTime.now().minusHours(1));
        employeeList.add(emp2);
    }

    @Test
    void testGetEmployee() {
        when(employeeRepository.findAll()).thenReturn(employeeList);

        Flux<Employee> result = employeeController.getEmployee();

        assertEquals(employeeList.size(), result.collectList().block().size());
    }

    @Test
    void testCreateEmployee() {
    	Date input = new Date();
        Employee newEmployee = new Employee();
        newEmployee.setId(3L);
        newEmployee.setEmployeeId(1);
        newEmployee.setDate(input);
        newEmployee.setLoginTime(LocalDateTime.now().minusHours(3));
        newEmployee.setLogoutTime(LocalDateTime.now().minusHours(2));

        when(employeeRepository.findByEmployeeIdAndDate(anyLong(), any(Date.class))).thenReturn(Optional.empty());
        when(employeeService.generateSequence(Employee.SEQUENCE_NAME)).thenReturn(3L);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

        Mono<Employee> result = employeeController.createEmployee(newEmployee);

        assertEquals(newEmployee, result.block());
    }

    @Test
    void testDeleteEmployee() throws ResourceNotFoundException {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Mono<Boolean> result = employeeController.deleteEmployee(employeeId)
                .map(response -> response.get("deleted"));

        assertTrue(result.block());
        verify(employeeRepository, times(1)).delete(employee);
    }
}
