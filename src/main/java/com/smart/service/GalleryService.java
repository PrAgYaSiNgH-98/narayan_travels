package com.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.GalleryRepository;
import com.smart.entities.Gallery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GalleryService {

@Autowired
private GalleryRepository galleryRepository;

public List<Gallery> getAllGalleryItems() {
    return galleryRepository.findAll();
}

public Gallery getGalleryById(Long id) {
    Optional<Gallery> gallery = galleryRepository.findById(id);
    return gallery.orElse(null);
}

public boolean updateGalleryItem(Long id, String title, String description, MultipartFile image) throws IOException {
    Optional<Gallery> galleryOptional = galleryRepository.findById(id);
    if (galleryOptional.isPresent()) {
        Gallery gallery = galleryOptional.get();
        gallery.setTitle(title);
        gallery.setDescription(description);

        if (image != null && !image.isEmpty()) {
            gallery.setImage(image.getBytes());
        }

        galleryRepository.save(gallery);
        return true;
    }
    return false;
}

public void deleteGalleryItem(Long id) {
    galleryRepository.deleteById(id);
}

public byte[] getImageById(Long id) {
    Optional<Gallery> gallery = galleryRepository.findById(id);
    return gallery.map(Gallery::getImage).orElse(null);
}

public void updateGallery(Long id, Gallery updatedGallery) {
    Optional<Gallery> existingGallery = galleryRepository.findById(id);
    if (existingGallery.isPresent()) {
        Gallery gallery = existingGallery.get();
        gallery.setTitle(updatedGallery.getTitle());
        gallery.setDescription(updatedGallery.getDescription());
        galleryRepository.save(gallery);
    }
    
}
// Save or update gallery


// Save or update gallery
public Gallery saveGallery(Gallery gallery) {
    return galleryRepository.save(gallery);
}
}