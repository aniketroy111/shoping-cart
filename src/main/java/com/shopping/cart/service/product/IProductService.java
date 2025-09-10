package com.shopping.cart.service.product;

import com.shopping.cart.dto.ProductDto;
import com.shopping.cart.model.Product;
import com.shopping.cart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    ProductDto addProduct(ProductDto productDTO);
    Product findProductById(Long id);
    void deleteProduct(Long id);
    ProductDto updateProduct(ProductUpdateRequest request, Long productId);

    List<ProductDto> getAllProducts();
    List<ProductDto> getProductByCategory(String category);
    List<ProductDto> getProductByBrand(String brand);
    List<ProductDto> getProductByCategoryAndBrand(String category, String brand);
    List<ProductDto> getProductByName(String name);
    List<ProductDto> getProductByBrandName(String brand, String name);

    List<ProductDto> convertToProductListToDtoList(List<Product> products);

    ProductDto convertToDto(Product product);
}
