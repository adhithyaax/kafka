package com.employee.attendance.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.employee.attendance.model.Employee;
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long>{



	Optional<Employee> findByEmployeeIdAndDate(long id, Date date);

}
