package com.shopping.cart.service.product;

import com.shopping.cart.dto.ProductDTO;
import com.shopping.cart.model.Product;

import java.util.List;

public interface IProductService {

    Product addProduct(ProductDTO productDTO);
    Product getProductById(Long id);
    void deleteProduct(Long id);
    void updateProduct(ProductDTO productDTO,Long productId);

    List<Product> getAllProducts();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category,String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandName(String brand,String name);

}
