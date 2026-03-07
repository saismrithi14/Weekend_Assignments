package com.order_management.Order_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class getMemberDTO {
    private UUID memberId;
    private String fullName;
    private String email;
}
