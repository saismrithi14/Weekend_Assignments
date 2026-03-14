package ServiceReport.reports.model;

import ServiceReport.reports.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private UUID order_id;
    private UUID memberId;
    private Integer product_id;
    private String product_name;
    private Integer product_quantity;
    private double total_price;
    private OrderType order_type;
    private Status status;
    private LocalDateTime timestamp;
}
