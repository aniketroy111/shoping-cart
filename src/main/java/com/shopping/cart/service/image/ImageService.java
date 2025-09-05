package com.shopping.cart.service.image;

import com.shopping.cart.dto.ImageDTO;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Image;
import com.shopping.cart.model.Product;
import com.shopping.cart.repository.ImageRepository;
import com.shopping.cart.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public List<ImageDTO> saveImages(List<MultipartFile> files, Long id) {
        Product product = productService.getProductById(id);
        List<ImageDTO> saveImageDTOS = new ArrayList<>();
        try {
            for (MultipartFile file : files){
                Image image = new Image();
                image.setProduct(product);
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));

                String buildDownloadUrl = "/api/v1/images/image/download/";
                image.setDownloadedUrl(buildDownloadUrl+image.getId());

                Image saveImage = imageRepository.save(image);
                saveImage.setDownloadedUrl(buildDownloadUrl+saveImage.getId());
                imageRepository.save(saveImage);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(saveImage.getId());
                imageDTO.setImageName(saveImage.getFileName());
                imageDTO.setDownloadUrl(saveImage.getDownloadedUrl());

                saveImageDTOS.add(imageDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return saveImageDTOS;
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{throw  new ResourceNotFoundException("Image not found");});
    }

    @Override
    public Image updateImage(MultipartFile file, Long id) {
        Image image = getImageById(id);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
        }catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
