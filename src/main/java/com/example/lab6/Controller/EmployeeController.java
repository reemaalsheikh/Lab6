package com.example.lab6.Controller;

import com.example.lab6.API.ApiResponse;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList <Employee> employees = new ArrayList<>();

//1. (get)Get all employees: Retrieves a list of all employees.

    @GetMapping("/get")
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.status(200).body(employees);
    }

//2. (post)Add a new employee: Adds a new employee to the system.
    @PostMapping("/add")
public ResponseEntity addEmployee(@Valid @RequestBody Employee employee , Errors errors) {
if(errors.hasErrors()) {
    String message = errors.getFieldError().getDefaultMessage();
    return ResponseEntity.status(400).body(message);
}
employees.add(employee);
return ResponseEntity.status(200).body(new ApiResponse("Employee Added Successfuly! "));
    }

//3. (put)Update an employee: Updates an existing employee's information.
    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@PathVariable int index, @Valid @RequestBody Employee employee , Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.set(index, employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee Updated Successfuly! "));
    }
//4. (delete)Delete an employee: Deletes an employee from the system.
//    Note: ▪ Verify that the employee exists.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id) {
        for (Employee emp : employees) {
            if (!emp.getId().equals(id)) {
                return ResponseEntity.status(400).body(new ApiResponse("Employee does not exist!"));
            }
            else if (emp.getId().equals(id)) {
                employees.remove(emp);
                return ResponseEntity.status(400).body(new ApiResponse("Employee exists! Employee Deleted Successfuly! "));

            }
        }
        return ResponseEntity.status(200).body(new ApiResponse(""));
    }


//    5. Search Employees by Position: Retrieves a list of employees based on their position (supervisor or coordinator).
//    Note: ▪ Ensure that the position parameter is valid (either "supervisor" or "coordinator").
@GetMapping("/search/{position}")
    public ResponseEntity searchEmployeeByPosition (@PathVariable String position) {

    ArrayList<Employee> employees1 = new ArrayList<>();
    if (!position.equalsIgnoreCase("supervisor")&& !position.equalsIgnoreCase("coordinator")) {
        return ResponseEntity.status(400).body(new ApiResponse("Invalid Position! Ensure that the position parameter is valid (either supervisor or coordinator )"));
    }

    for (Employee emp : employees) {
    if (emp.getPosition().equalsIgnoreCase(position)) {
    employees1.add(emp);
   }}

     if (employees1.isEmpty()){
    return ResponseEntity.status(400).body(new ApiResponse("No employee with this Position!  " + position));
     }
    return ResponseEntity.status(200).body(employees1);
    }



//    6. Get Employees by Age Range: Retrieves a list of employees within a specified age range.
//    Note:▪ Ensure that minAge and maxAge are valid age values.
    @GetMapping("/get/{minage}/{maxage}")
    public ResponseEntity getEmployeeByAgeRange (@PathVariable int minage , @PathVariable int maxage){
        ArrayList<Employee> employees1 = new ArrayList<>();

            if (minage <=25 || maxage >65 ) {
                return ResponseEntity.status(400).body(new ApiResponse("Age cannot be less than 25 or more than 65!"));
            }

        for (Employee emp : employees) {
            if (emp.getAge() >= minage && emp.getAge() <= maxage) {
                employees1.add(emp);
            }
        }
//         if(employees1.isEmpty()){
//            return ResponseEntity.status(400).body(new ApiResponse("No available emoloyee with this age "));
//        }
        return ResponseEntity.status(200).body(employees1);
    }



//7. Apply for annual leave: Allow employees to apply for annual leave.
//    Note: ▪ Verify that the employee exists.
//▪ The employee must not be on leave (the onLeave flag must be false).
//            ▪ The employee must have at least one day of annual leave remaining.
//            ▪ Behavior:
//            ▪ Set the onLeave flag to true.
//            ▪ Reduce the annualLeave by 1.


    @PutMapping("/update/Annual/{id}")
    public ResponseEntity applyAnnualL (@PathVariable String id){

        for(Employee emp : employees){
            if(emp.getId().equals(id)){
            if(emp.getOnLeave().equals(false)){
                if(emp.getAnnualLeave() >= 1){
                    emp.setOnLeave(true);
                    emp.setAnnualLeave(emp.getAnnualLeave()-1);
                    return ResponseEntity.status(200).body(new ApiResponse("Annual leave successfuly updated!"));
                }
            }}
        }
       return ResponseEntity.status(400).body(new ApiResponse("Employee not found!"));
    }

//8. Get Employees with No Annual Leave: Retrieves a list of employees who have
//used up all their annual leave.
@GetMapping("/get/noannual")
    public ResponseEntity getEmployeeswithNoAnnualLeave (){
        ArrayList<Employee> employees1 = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getAnnualLeave() == 0) {
                employees1.add(emp);
            }

        }
    return ResponseEntity.status(200).body(employees1);
}



//9. Promote Employee: Allows a supervisor to promote an employee to the position of emp.getId().equals(secondId) if they meet certain criteria.
// Note: ▪ Verify that the employee with the specified ID exists.
//▪ Ensure that the requester (user making the request) is a supervisor.
//▪ Validate that the employee's age is at least 30 years.
//▪ Confirm that the employee is not currently on leave.
//▪ Change the employee's position to "supervisor" if they meet the criteria.
    @PutMapping("/promotion/{firstId}/{secondId}")
    public ResponseEntity employeePromotion (@PathVariable String firstId,@PathVariable String secondId) {
        //check for supervisor firstId
        for (Employee emp : employees) {
            if (emp.getId().equals(firstId)) {
            if (emp.getPosition().equals("supervisor")) {

                 for (Employee emp1 : employees) {
                     if (emp1.getId().equals(secondId) && emp1.getPosition().equals("coordinator") && (emp1.getAge() >= 30) && (emp1.getOnLeave().equals(false)) ) {
                        emp1.setPosition("supervisor");
                        return ResponseEntity.status(200).body(emp1);
                     }

                    }
                }
            }
        }

        //check for secondId

        return ResponseEntity.status(400).body(new ApiResponse("Employee not found!"));

    }




    }








