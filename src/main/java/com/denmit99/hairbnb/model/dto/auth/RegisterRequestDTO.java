package com.denmit99.hairbnb.model.dto.auth;

import com.denmit99.hairbnb.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @Email
    @NotBlank
    private String email;

    //TODO Do we need it?
    private String firstName;

    //TODO do we need it?
    private String lastName;

    @NotBlank
    private String password;

    @NotNull
    private UserRole role;
}
