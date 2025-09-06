package com.shopping.cart.controller;


import com.shopping.cart.Response.ApiResponse;
import com.shopping.cart.dto.ProductDTO;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Product;
import com.shopping.cart.request.ProductUpdateRequest;
import com.shopping.cart.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct(){
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Fetched Successfully",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product product = productService.findProductById(id);
            ProductDTO productDTO = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Fetched Successfully",productDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@RequestParam String categoryName){
        try {
            List<ProductDTO> products = productService.getProductByCategory(categoryName);
            return ResponseEntity.ok(new ApiResponse("Fetched Successfully",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @GetMapping("/by-brand-and-name")
    public ResponseEntity<ApiResponse> getProductNameAndBrand(@RequestParam String bramd,@RequestParam String name){
        try {
            List<ProductDTO> products = productService.getProductByBrandName(bramd, name);
            return ResponseEntity.ok(new ApiResponse("Fetched Successfully",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name){
        try {
            List<ProductDTO> products = productService.getProductByName(name);
            return ResponseEntity.ok(new ApiResponse("Fetched Successfully",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
        try {
            List<ProductDTO> products = productService.getProductByBrand(brand);
            return ResponseEntity.ok(new ApiResponse("Fetched Successfully",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String category,@RequestParam String brand){
        try {
            List<ProductDTO> products = productService.getProductByCategoryAndBrand(category,brand);
            return ResponseEntity.ok(new ApiResponse("Fetched Successfully",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }





    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDTO productDTO){
        try {
            ProductDTO products = productService.addProduct(productDTO);
            return ResponseEntity.ok(new ApiResponse("Added Successfully",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id,@RequestBody ProductUpdateRequest request){
        try {
            ProductDTO product = productService.updateProduct(request,id);
            return ResponseEntity.ok(new ApiResponse("Updated Successfully",product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


}
