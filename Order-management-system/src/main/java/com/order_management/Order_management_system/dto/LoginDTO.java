package com.order_management.Order_management_system.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank(message = "The name shouldn't be blank")
    private String username;

    @NotBlank(message="Password shouldn't be blank")
    @Size(min=6, message="The password should be atleast 6 characters long")
    private String password;

}
