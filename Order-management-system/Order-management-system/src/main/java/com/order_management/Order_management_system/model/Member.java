package com.order_management.Order_management_system.model;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private UUID memberId;
    private String fullName;
    private String email;
    private String password;

}
