package com.shopping.cart.controller;

import com.shopping.cart.Response.ApiResponse;
import com.shopping.cart.dto.ImageDTO;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Image;
import com.shopping.cart.service.image.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api.prefix}/images")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try{
            List<ImageDTO> imageDTOS = imageService.saveImages(files,productId);
            return ResponseEntity.ok(new ApiResponse("Upload successfull",imageDTOS));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() +"\"")
                .body(resource);

    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestParam MultipartFile files){
        try {
            Image image = imageService.getImageById(imageId);
            if(image!=null){
                imageService.updateImage(files,imageId);
                return ResponseEntity.ok(new ApiResponse("Image Updated",null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed",INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try {
            Image image = imageService.getImageById(imageId);
            if(image!=null){
                imageService.deleteImage(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Success",null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Failed",INTERNAL_SERVER_ERROR));
    }

}
