package com.shopping.cart.service.product;

import com.shopping.cart.dto.ProductDTO;
import com.shopping.cart.model.Product;
import com.shopping.cart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    ProductDTO addProduct(ProductDTO productDTO);
    Product findProductById(Long id);
    void deleteProduct(Long id);
    ProductDTO updateProduct(ProductUpdateRequest request, Long productId);

    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductByCategory(String category);
    List<ProductDTO> getProductByBrand(String brand);
    List<ProductDTO> getProductByCategoryAndBrand(String category,String brand);
    List<ProductDTO> getProductByName(String name);
    List<ProductDTO> getProductByBrandName(String brand,String name);

    List<ProductDTO> convertToProductListToDtoList(List<Product> products);

    ProductDTO convertToDto(Product product);
}
