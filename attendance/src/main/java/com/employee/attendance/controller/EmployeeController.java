package com.employee.attendance.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.attendance.EmployeeService;
import com.employee.attendance.exception.ResourceNotFoundException;
import com.employee.attendance.model.Employee;
import com.employee.attendance.repository.EmployeeRepository;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
	 @Autowired
	    KafkaTemplate<String, Employee> KafkaJsontemplate;
	    String TOPIC_NAME = "emp-topic";
	   @Autowired
	    private EmployeeService sequenceGeneratorService;
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employee")
	public Flux<Employee> getEmployee() {
		List<Employee> det=employeeRepository.findAll();
		det.forEach(x ->{
			if(x.getLoginTime() !=null) {
				LocalDateTime tempDateTime = LocalDateTime.from(x.getLoginTime());
				long hours = tempDateTime.until( x.getLogoutTime(), ChronoUnit.HOURS );
				System.out.println("hours >"+hours);	
			}
			
		});
		

		return Flux.fromIterable(employeeRepository.findAll());

	}

	@PostMapping("/employees")
	public Mono<Employee> createEmployee(@Valid @RequestBody Employee employee) {
	
		Optional<Employee> emp = employeeRepository.findByEmployeeIdAndDate(employee.getEmployeeId(),employee.getDate());
		if(emp.isPresent()) {
			emp.get().setLogoutTime(employee.getLogoutTime());
			KafkaJsontemplate.send(TOPIC_NAME,emp.get());
			return Mono.just(employeeRepository.save(emp.get()));			
		}else {
			employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
			KafkaJsontemplate.send(TOPIC_NAME,employee);
			return Mono.just(employeeRepository.save(employee));
		}
		
	
	}
	
	@DeleteMapping("/employees/{id}")
    public Mono<Map<String, Boolean>> deleteEmployee(@PathVariable(value = "id") Long employeeId)
    throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return Mono.just(response);
    }

	
}
