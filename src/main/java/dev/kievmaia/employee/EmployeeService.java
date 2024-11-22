package dev.kievmaia.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    // Find all employees matching exact criteria
    public List<Employee> findEmployeesByExample(Employee employee) {
        var example = this.createExample(employee);
        return employeeRepository.findAll(example);
    }

    // Find a single employee with example
    public Employee findOneEmployeeByExample(Employee employee) {
        var example = this.createExample(employee);
        return employeeRepository.findOne(example)
                .orElseThrow(() -> new EmployeeNotFoundException("No employee found matching the example"));
    }

    // Find employees with custom matching rules
    public List<Employee> findEmployeesWithCustomMatcher(String firstName, String department) {
        var employee = Employee.builder()
                .firstName(firstName)
                .department(department)
                .build();

        // Create a custom ExampleMatcher
        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()                                                                       // Ignore case for all string matches
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)                             // Use LIKE %value% for strings
                .withIgnoreNullValues()                                                                 // Ignore null values
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatcher::exact)      // Exact match for firstName
                .withMatcher("department", ExampleMatcher.GenericPropertyMatcher::contains); // Partial match for department

        var example = Example.of(employee, matcher);
        return employeeRepository.findAll(example);
    }

    // Count employees matching example
    public Long countEmployeesByExample(Employee employee) {
        var example = this.createExample(employee);
        return employeeRepository.count(example);
    }

    // Check if any employees match the example
    public boolean existsEmployeeByExample(Employee employee) {
        var example = this.createExample(employee);
        return employeeRepository.exists(example);
    }

    private Example<Employee> createExample(Employee employee) {
        return Example.of(employee);
    }
}