package ServiceOrder.orders.client;

import ServiceOrder.orders.dto.ProductDTO;
import ServiceOrder.orders.dto.ProductQuantityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="product-service", url="http://localhost:8083")
public interface ProductClient
{
    @GetMapping("/oms/products/contains/{product_name}")
    ProductDTO getProductInfoByNameIfExists(@PathVariable String product_name);

    @PutMapping("/oms/products/update")
    double updateProductTable(@RequestBody ProductQuantityDTO dto);

    @DeleteMapping("/oms/products/update-table")
    void updateProductTableDelete(@RequestBody ProductQuantityDTO dto);

}
