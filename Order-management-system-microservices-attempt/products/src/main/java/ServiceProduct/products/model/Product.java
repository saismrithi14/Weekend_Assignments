package ServiceProduct.products.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer product_id;
    private String product_name;
    private Integer availableQuantity;
    private double product_price;

}
