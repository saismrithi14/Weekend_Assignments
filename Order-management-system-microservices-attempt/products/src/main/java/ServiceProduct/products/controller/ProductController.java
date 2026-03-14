package ServiceProduct.products.controller;
import ServiceProduct.products.dto.ProductDTO;
import ServiceProduct.products.dto.ProductQuantityDTO;
import ServiceProduct.products.model.Product;
import ServiceProduct.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/oms/products")
public class ProductController {
    @Autowired
    ProductService productService;

    /*
    @GetMapping("/contains/{product_name}")
    public boolean containsProduct(@PathVariable String product_name)
    {
        return productService.containsProduct(product_name);
    }

    */
    @GetMapping("/return/{product_name}")
    public Integer returnId(@PathVariable String product_name)
    {
        return productService.returnId(product_name);
    }

    @GetMapping("/all")
    public ConcurrentHashMap<Integer, Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/contains/{product_name}")
    public ProductDTO getProductInfoByNameIfExists(@PathVariable String product_name)
    {
        return productService.getProductInfoByNameIfExists(product_name);
    }

    @PutMapping("/update")
    public double updateProductTable(@RequestBody ProductQuantityDTO dto)
    {
        return productService.updateProductTable(dto);
    }

    @DeleteMapping("/update-table")
    public void updateProductTableDelete(@RequestBody ProductQuantityDTO dto)
    {
        productService.updateProductTableDelete(dto);
    }

}
