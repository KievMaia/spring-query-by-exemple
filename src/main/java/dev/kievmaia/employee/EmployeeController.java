package dev.kievmaia.employee;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/search/example")
    public List<Employee> findByExample(@RequestBody @Valid Employee employee) {
        return employeeService.findEmployeesByExample(employee);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String department) {
        var employees = employeeService.findEmployeesWithCustomMatcher(firstName, department);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/search/example/one")
    public ResponseEntity<Employee> searchOneEmployee(@RequestBody @Valid Employee employee) {
        var employees = employeeService.findOneEmployeeByExample(employee);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/count")
    public long countEmployeesByExample(@RequestBody @Valid Employee employee) {
        return employeeService.countEmployeesByExample(employee);
    }

    @PostMapping("/exists")
    public boolean existsByExample(@RequestBody @Valid Employee employee) {
        return employeeService.existsEmployeeByExample(employee);
    }
}
