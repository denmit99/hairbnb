package com.denmit99.hairbnb.model.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBO {
    private String email;

    private String firstName;

    private String lastName;
}
