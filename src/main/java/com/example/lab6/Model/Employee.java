package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {

    @NotEmpty(message= "Id should not be Empty!")
    @Size(min=3, max=20, message = "Id Length must be more than 2 characters!")
    private String id;

    @NotEmpty(message= "Name should not be Empty!")
    @Size(min=5)
    @Pattern(regexp="^[a-zA-Z]*$", message ="Name Must contain characters only (no numbers)!" )
    private String name;

    @NotEmpty(message= "email should not be Empty!")
    @Email
    private String email;

    @NotEmpty(message = "Phone number cannot be Empty!")
    @Pattern(regexp = "^05\\d{8}$",message = "Must start with 05, consists of exactly 10 digits")
    private String phonenumber;

    @NotNull(message= "age should not be Empty!")
    @Positive(message= "You Must enter numbers Only!")
    @Min(value = 26, message = "Age Must be more than 25.")
    @Max(value= 65)
    private int age;

    @NotEmpty(message = "Position should not be Empty!")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Two valid inputs: supervisor or coordinator only")
    private String position;

    @AssertFalse
    private Boolean onLeave;

    @NotNull(message = "Hire date should not be Empty!")
    @PastOrPresent
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate hireDate;

    @NotNull(message= "AnnualLeave should not be Null!")
    @Positive(message= "You Must enter positive numbers Only!")
    private int annualLeave;


}
