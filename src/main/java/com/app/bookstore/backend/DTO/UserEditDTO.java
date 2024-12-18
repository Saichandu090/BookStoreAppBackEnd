package com.app.bookstore.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDTO
{
    @Pattern(regexp = "^[A-Z][A-Za-z .]{2,}$",message = "First Name Should start with Capital and AtLeast contain 3 characters")
    private String firstName;
    @Pattern(regexp = "^[A-Z][A-Za-z .]{2,}$",message = "Last Name Should start with Capital and AtLeast contain 3 characters")
    private String lastName;

    @Past
    private LocalDate dob;
}
