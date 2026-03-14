package ServiceProduct.products.service;

import ServiceProduct.products.dto.ProductDTO;
import ServiceProduct.products.dto.ProductQuantityDTO;
import ServiceProduct.products.enums.OrderType;
import ServiceProduct.products.exceptions.ProductNotFoundException;
import ServiceProduct.products.exceptions.QuantityLimitExceededException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ServiceProduct.products.model.Product;

import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductService {
    private ConcurrentHashMap<Integer, Product> productTable = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> nameToIdMapping = new ConcurrentHashMap<>();

    @PostConstruct
    public void initProducts()
    {
        addProduct(new Product(1, "iPhone 15", 50, 1200.00));
        addProduct(new Product(2, "Samsung Galaxy S24", 40, 1100.00));
        addProduct(new Product(3, "MacBook Pro", 25, 2500.00));
        addProduct(new Product(4, "Dell XPS 15", 30, 2200.00));
        addProduct(new Product(5, "Sony Headphones", 100, 300.00));
        addProduct(new Product(6, "iPad Air", 60, 800.00));
        addProduct(new Product(7, "PlayStation 5", 35, 600.00));
        addProduct(new Product(8, "Logitech Mouse", 200, 50.00));
        addProduct(new Product(9, "Mechanical Keyboard", 120, 150.00));
        addProduct(new Product(10, "4K Monitor", 45, 450.00));
    }

    private void addProduct(Product P)
    {
        productTable.put(P.getProduct_id(), P);
        nameToIdMapping.put(P.getProduct_name(),P.getProduct_id());
    }

    public boolean containsProduct(String product_name)
    {
        return nameToIdMapping.containsKey(product_name);
    }

    public Integer returnId(String product_name)
    {
        return nameToIdMapping.get(product_name);
    }

    public ConcurrentHashMap<Integer, Product> getAllProducts()
    {
        return productTable;
    }

    public ProductDTO getProductInfoByNameIfExists(String product_name)
    {
        if(!containsProduct(product_name))
        {
           return null;
        }

        else {
            ProductDTO dto = new ProductDTO();
            dto.setProduct_name(product_name);
            dto.setProduct_id(nameToIdMapping.get(product_name));
            return dto;
        }
    }

    public double updateProductTable(ProductQuantityDTO dto)
    {
        Integer product_id = dto.getProduct_id();
        if(dto.getOrder_type() == OrderType.BUY)
        {
            if(productTable.get(product_id).getAvailableQuantity() < dto.getProduct_quantity())
            {
                throw new QuantityLimitExceededException("Quantity desired exceeds the available amount");
            }

            else {
                int remaining = productTable.get(product_id).getAvailableQuantity() - dto.getProduct_quantity();
                productTable.get(product_id).setAvailableQuantity(remaining);


            }
        }

        else {
            productTable.get(product_id).setAvailableQuantity(productTable.get(product_id).getAvailableQuantity() + dto.getProduct_quantity());
        }

        double price = dto.getProduct_quantity() * productTable.get(product_id).getProduct_price();
        return price;
    }

    public void updateProductTableDelete(ProductQuantityDTO dto)
    {
        Integer product_id = dto.getProduct_id();
        Integer available_quantity = productTable.get(product_id).getAvailableQuantity();
        if(dto.getOrder_type() == OrderType.BUY)
        {
            productTable.get(product_id).setAvailableQuantity(dto.getProduct_quantity() + available_quantity);
        }

        else {
            productTable.get(product_id).setAvailableQuantity(available_quantity - dto.getProduct_quantity());
        }
    }

}
