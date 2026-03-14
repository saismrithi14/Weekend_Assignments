package ServiceOrder.orders.dto;

import ServiceOrder.orders.enums.OrderType;
import ServiceOrder.orders.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Valid
    @NotNull(message = "The UUID shouldn't be null")
    private UUID memberId;

    @Valid
    @NotBlank(message="product_name should not be empty")
    private String product_name;

    @Valid
    @Positive
    private Integer product_quantity;

    private OrderType order_type;
    private Status status;

}
