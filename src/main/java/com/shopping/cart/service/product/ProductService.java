package com.shopping.cart.service.product;

import com.shopping.cart.dto.ProductDTO;
import com.shopping.cart.exceptions.ProductNotFound;
import com.shopping.cart.model.Category;
import com.shopping.cart.model.Product;
import com.shopping.cart.repository.CategoryRepository;
import com.shopping.cart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(ProductDTO productDTO) {

        // check the product category already exist or not in DB
        // if yes then set as new product in that category
        // if no save the new category
        // then set the new product category
        Category category =  Optional.ofNullable(categoryRepository.findByName(productDTO.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(productDTO.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        productDTO.setCategory(category);

        return productRepository.save(createProduct(productDTO));
    }


    private Product createProduct(ProductDTO productDTO) {

        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        return product;
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFound("Product not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ()-> {throw new ProductNotFound("Product not found");});
    }

    @Override
    public void updateProduct(ProductDTO productDTO, Long productId) {

        // check the requested product is exist or not in DB
        // if yes then update the product full or partial according to request
        // if no then return exception
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFound("Product not found"));

        if(productDTO.getCategory()!= null){
            product.setCategory(productDTO.getCategory());
        }
        if(productDTO.getName()!= null){
            product.setName(productDTO.getName());
        }
        if(productDTO.getBrand()!= null){
            product.setBrand(productDTO.getBrand());
        }
        if(productDTO.getPrice()!= null){
            product.setPrice(productDTO.getPrice());
        }
        if(productDTO.getDescription()!= null){
            product.setDescription(productDTO.getDescription());
        }
        if(productDTO.getInventory()!= null){
            product.setInventory(productDTO.getInventory());
        }
        if(productDTO.getImages()!= null){
            product.setImages(productDTO.getImages());
        }


    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductByBrandName(String brand, String name) {
        return List.of();
    }
}
