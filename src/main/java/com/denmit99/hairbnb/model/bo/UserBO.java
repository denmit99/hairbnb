package com.denmit99.hairbnb.model.bo;

import com.denmit99.hairbnb.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBO {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private UserRole role;
}
