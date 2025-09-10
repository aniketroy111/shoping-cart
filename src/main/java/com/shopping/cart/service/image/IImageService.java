package com.shopping.cart.service.image;

import com.shopping.cart.dto.ImageDto;
import com.shopping.cart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long id);
    void deleteImage(Long id);
    Image updateImage(MultipartFile file,Long id);
}
