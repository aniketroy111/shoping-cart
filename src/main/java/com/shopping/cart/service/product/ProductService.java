package com.shopping.cart.service.product;

import com.shopping.cart.dto.ImageDto;
import com.shopping.cart.dto.ProductDto;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Category;
import com.shopping.cart.model.Image;
import com.shopping.cart.model.Product;
import com.shopping.cart.repository.CategoryRepository;
import com.shopping.cart.repository.ProductRepository;
import com.shopping.cart.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(ProductDto productDTO) {

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
        Product product = productRepository.save(createProduct(productDTO));
        return convertToDto(product);
    }


    private Product createProduct(ProductDto productDTO) {

        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        return product;
    }


    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ()-> {throw new ResourceNotFoundException("Product not found");});
    }

    @Override
    public ProductDto updateProduct(ProductUpdateRequest request, Long productId) {

        // check the requested product is exist or not in DB
        // if yes then update the product full or partial according to request
        // if no then return exception
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found"));

        if(request.getCategory()!= null){
            product.setCategory(request.getCategory());
        }
        if(request.getName()!= null){
            product.setName(request.getName());
        }
        if(request.getBrand()!= null){
            product.setBrand(request.getBrand());
        }
        if(request.getPrice()!= null){
            product.setPrice(request.getPrice());
        }
        if(request.getDescription()!= null){
            product.setDescription(request.getDescription());
        }
        if(request.getInventory()!= null){
            product.setInventory(request.getInventory());
        }
        if(request.getImages()!= null){
            product.setImages(request.getImages());
        }
        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<ProductDto> getProductByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        return convertToProductListToDtoList(products);
    }

    @Override
    public List<ProductDto> getProductByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        return convertToProductListToDtoList(products);
    }

    @Override
    public List<ProductDto> getProductByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrand(category,brand);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        return convertToProductListToDtoList(products);
    }

    @Override
    public List<ProductDto> getProductByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        return convertToProductListToDtoList(products);
    }

    @Override
    public List<ProductDto> getProductByBrandName(String brand, String name) {
        List<Product> products= productRepository.findProductByBrandAndName(brand,name);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        return convertToProductListToDtoList(products);
    }

    @Override
    public List<ProductDto> convertToProductListToDtoList(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDTO = modelMapper.map(product, ProductDto.class);
        List<Image> images = product.getImages();
        if(images !=null &&!images.isEmpty()){
            List<ImageDto> imageDTOs = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
            productDTO.setImages(imageDTOs);
        }
        return productDTO;
    }
}
