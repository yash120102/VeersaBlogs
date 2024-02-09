package com.veersablog.BlogApp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 4 , message = "name must be min of 4 characters !!")
    private String name;
    @Email(message = "email address is invaid")
    private String email;
    @NotEmpty
    @Size(min = 3 , max = 10 , message = "password must be min of 3 character and max of 10 !!")
    private String password;
    @NotEmpty
    private String about;

}
