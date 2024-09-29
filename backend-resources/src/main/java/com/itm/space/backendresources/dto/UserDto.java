package com.itm.space.backendresources.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
