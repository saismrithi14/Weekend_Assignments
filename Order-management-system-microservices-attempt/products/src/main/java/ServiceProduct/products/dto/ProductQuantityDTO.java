package ServiceProduct.products.dto;

import ServiceProduct.products.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDTO {
    private Integer product_id;
    private OrderType order_type;
    private Integer product_quantity;

}
